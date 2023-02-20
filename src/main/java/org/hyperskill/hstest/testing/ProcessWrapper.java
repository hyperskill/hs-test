package org.hyperskill.hstest.testing;

import lombok.Getter;
import lombok.Setter;
import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.common.OsUtils;
import org.hyperskill.hstest.dynamic.security.ExitException;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.stage.StageTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hyperskill.hstest.common.Utils.sleep;

public class ProcessWrapper {

    private Process process;
    private final String[] args;
    private final String command;
    @Getter private ThreadGroup group;

    private final ByteArrayOutputStream stdout = new ByteArrayOutputStream();
    private final ByteArrayOutputStream stderr = new ByteArrayOutputStream();
    private OutputStream stdin;

    private boolean alive = true;
    private final AtomicInteger pipesWatching = new AtomicInteger(0);
    private boolean terminated = false;

    private final Queue<Long> cpuLoadHistory = new ConcurrentLinkedQueue<>();
    private final int cpuLoadHistoryMax = 2;

    private final Queue<Integer> outputDiffHistory = new ConcurrentLinkedQueue<>();
    private final int outputDiffHistoryMax = 2;

    public static boolean initialIdleWait = true;
    public static int initialIdleWaitTime = 150;

    @Getter @Setter boolean checkEarlyFinish = false;
    @Getter @Setter boolean registerOutput = true;

    public ProcessWrapper(List<String> args) {
        this(args.toArray(String[]::new));
    }

    public ProcessWrapper(String... args) {
        this(null, args);
    }

    public ProcessWrapper(ThreadGroup group, List<String> args) {
        this(group, args.toArray(String[]::new));
    }

    public ProcessWrapper(ThreadGroup group, String... args) {
        this.args = args;
        this.group = group;
        command = String.join(" ", args);
    }

    public String getStdout() {
        return stdout.toString();
    }

    public String getStderr() {
        return stderr.toString();
    }

    public void provideInput(ByteArrayOutputStream input) {
        try {
            stdin.write(input.toByteArray());
            stdin.flush();
        } catch (IOException e) {
            StageTest.getCurrTestRun().setErrorInTest(
                new UnexpectedError("Can't provide input to the process\n" + command));
        }
    }

    public ProcessWrapper start() {
        if (process != null) {
            throw new UnexpectedError(
                "Cannot start the same process twice\n" + command);
        }

        try {
            List<String> fullArgs = new ArrayList<>();

            if (OsUtils.isWindows()) {
                // To test this in windows you need WSL2 installed
                // fullArgs.add("bash");
                // fullArgs.add("-c");
                fullArgs.add("cmd");
                fullArgs.add("/c");
            }

            fullArgs.addAll(List.of(args));

            process = new ProcessBuilder(fullArgs)
                .directory(new File(FileUtils.cwd()))
                .start();

            stdin = process.getOutputStream();

        } catch (Exception ex) {
            StageTest.getCurrTestRun().setErrorInTest(
                new UnexpectedError("Cannot start process\n" + command, ex));
            alive = false;
            terminated = true;
            return this;
        }

        if (group == null) {
            group = new ThreadGroup(args[0]);
        }

        Thread checkCpuLoad = new Thread(group, this::checkCpuLoad);
        checkCpuLoad.setDaemon(true);
        checkCpuLoad.start();

        Thread checkOutput = new Thread(group, this::checkOutput);
        checkOutput.setDaemon(true);
        checkOutput.start();

        Thread checkStdout = new Thread(group, this::checkStdout);
        checkStdout.setDaemon(true);
        checkStdout.start();

        Thread checkStderr = new Thread(group, this::checkStderr);
        checkStderr.setDaemon(true);
        checkStderr.start();

        return this;
    }

    public void checkAlive() {
        if (alive && !process.isAlive()) {
            alive = false;
        }
    }

    private void checkPipe(InputStream readPipe,
                           OutputStream writePipe,
                           ByteArrayOutputStream copyWritePipe) {

        pipesWatching.incrementAndGet();

        while (true) {

            int newOutput;
            try {
                newOutput = readPipe.read();
            } catch (IOException ex) {
                if (isFinished(false)) {
                    break;
                }
                continue;
            }

            if (newOutput == -1) {
                pipesWatching.decrementAndGet();

                if (pipesWatching.get() == 0) {
                    alive = false;
                    terminate();
                }

                break;
            }

            try {
                if (registerOutput) {
                    writePipe.write(newOutput);
                }
            } catch (IOException ex) {
                StageTest.getCurrTestRun().setErrorInTest(
                    new UnexpectedError("IOException writing to stdout\n" + command, ex));
                alive = false;
                terminate();
                break;
            } catch (ExitException ex) {
                alive = false;
                terminate();
                break;
            }

            copyWritePipe.write(newOutput);
        }
    }

    private void checkStdout() {
        checkPipe(process.getInputStream(), System.out, stdout);
    }

    private void checkStderr() {
        checkPipe(process.getErrorStream(), System.err, stderr);
    }

    private void checkCpuLoad() {
        long oldCpuTime = 0;
        while (alive) {
            var duration = process.info().totalCpuDuration();

            if (duration.isEmpty()) {
                waitOutput();
                alive = false;
                break;
            }

            long currCpuTime = duration.get().getSeconds() * 1_000_000_000 + duration.get().getNano();
            long currCpuLoad = currCpuTime - oldCpuTime;
            oldCpuTime = currCpuTime;

            if (!initialIdleWait) {
                cpuLoadHistory.add(currCpuLoad);
            }

            if (cpuLoadHistory.size() > cpuLoadHistoryMax) {
                cpuLoadHistory.remove();
            }

            sleep(10);
            checkAlive();

            if (initialIdleWait) {
                initialIdleWaitTime--;
                if (initialIdleWaitTime == 0) {
                    initialIdleWait = false;
                }
            }
        }
    }

    private void checkOutput() {
        int oldOutputSize = stdout.size();

        while (alive) {
            int currOutputSize = stdout.size();
            int diff = currOutputSize - oldOutputSize;
            oldOutputSize = currOutputSize;

            if (!initialIdleWait) {
                outputDiffHistory.add(diff);
            }

            if (outputDiffHistory.size() > outputDiffHistoryMax) {
                outputDiffHistory.remove();
            }

            if (initialIdleWait && diff > 0) {
                initialIdleWait = false;
            }

            sleep(10);
            checkAlive();
        }
    }

    public boolean isWaitingInput() {
        if (initialIdleWait) {
            return false;
        }

        boolean programNotLoadingProcessor =
            cpuLoadHistory.size() >= cpuLoadHistoryMax
            && cpuLoadHistory.stream().mapToLong(e -> e).sum() < 1;

        boolean programNotPrintingAnything =
            outputDiffHistory.size() >= outputDiffHistoryMax
            && outputDiffHistory.stream().mapToInt(e -> e).sum() == 0;

        return programNotLoadingProcessor && programNotPrintingAnything;
    }

    public void registerInputRequest() {
        if (!isWaitingInput()) {
            throw new UnexpectedError("Program is not waiting for the input\n" + command);
        }
        cpuLoadHistory.clear();
        outputDiffHistory.clear();
    }

    public boolean isFinished() {
        return isFinished(true);
    }

    public boolean isFinished(boolean needWaitOutput) {
        if (!checkEarlyFinish) {
            return !alive;
        }

        if (!alive) {
            return true;
        }

        if (!process.isAlive()) {
            alive = false;
        }

        if (!alive && needWaitOutput) {
            waitOutput();
        }

        return !alive;
    }

    public synchronized void terminate() {
        if (terminated) {
            return;
        }

        waitOutput();

        alive = false;

        process.descendants().forEach(pr -> {
            if (!pr.destroy()) {
                pr.destroyForcibly();
            }
        });

        var pr = process.toHandle();
        if (!pr.destroy()) {
            pr.destroyForcibly();
        }

        terminated = true;
    }

    private void waitOutput() {
        int iterations = 50;
        int sleepTime = 50;

        int oldStdoutSize = stdout.size();
        int oldStderrSize = stderr.size();

        while (iterations != 0) {
            sleep(sleepTime);

            int currStdoutSize = stdout.size();
            int currStderrSize = stderr.size();

            if (currStdoutSize == oldStdoutSize && currStderrSize == oldStderrSize) {
                break;
            }

            oldStdoutSize = currStdoutSize;
            oldStderrSize = currStderrSize;
            iterations--;
        }
    }

    public void waitFor() {
        while (!isFinished()) {
            sleep(10);
        }
        waitOutput();
    }

    public boolean isErrorHappened() {
        return !alive && stderr.size() > 0
            && !process.isAlive() && process.exitValue() != 0;
    }
}
