package org.hyperskill.hstest.testing.execution;

import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.input.InputHandler;
import org.hyperskill.hstest.dynamic.security.ExitException;
import org.hyperskill.hstest.dynamic.security.TestingSecurityManager;
import org.hyperskill.hstest.exception.outcomes.CompilationError;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.OutOfInputError;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.ProcessWrapper;
import org.hyperskill.hstest.testing.execution.runnable.RunnableFile;

import java.util.List;

import static org.hyperskill.hstest.common.Utils.sleep;
import static org.hyperskill.hstest.common.Utils.tryManyTimes;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.COMPILATION_ERROR;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.EXCEPTION_THROWN;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.FINISHED;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.RUNNING;

public abstract class ProcessExecutor extends ProgramExecutor {

    private static boolean compiled = false;

    private ProcessWrapper process = null;
    private Thread thread = null;
    private ThreadGroup group;

    private boolean continueExecuting = true;
    protected final RunnableFile runnable;

    protected ProcessExecutor(RunnableFile runnable) {
        this.runnable = runnable;
    }

    protected List<String> compilationCommand() {
        return List.of();
    }

    protected String filterCompilationError(String error) {
        return error;
    }

    abstract protected List<String> executionCommand(List<String> args);

    protected void cleanup() {

    }

    private boolean compileProgram() {
        if (compiled) {
            return true;
        }

        var command = compilationCommand();

        if (command.size() == 0) {
            return true;
        }

        process = new ProcessWrapper(command);
        process.setRegisterOutput(false);
        process.start();
        process.waitFor();

        if (process.isErrorHappened()) {
            String errorText = filterCompilationError(process.getStderr());

            StageTest.getCurrTestRun().setErrorInTest(new CompilationError(errorText));
            machine.setState(COMPILATION_ERROR);
            return false;
        }

        return true;
    }

    private void handleProcess(List<String> args) {
        String oldWorkingDirectory = FileUtils.cwd();

        try {
            FileUtils.chdir(runnable.getFolder());

            if (!compileProgram()) {
                return;
            }

            compiled = true;

            var command = executionCommand(args);

            machine.setState(RUNNING);
            process = new ProcessWrapper(group, command).start();

            while (continueExecuting) {
                sleep(1);

                if (process.isFinished()) {
                    break;
                }

                boolean isInputAllowed = isInputAllowed();
                boolean isWaitingInput = process.isWaitingInput();

                if (isInputAllowed && isWaitingInput) {
                    process.registerInputRequest();

                    try {
                        var nextInput = InputHandler.readline();
                        process.provideInput(nextInput);
                    } catch (ExitException ex) {
                        if (waitIfTerminated()) {
                            if (StageTest.getCurrTestRun().getErrorInTest() instanceof OutOfInputError) {
                                StageTest.getCurrTestRun().setErrorInTest(null);
                            }
                            break;
                        }
                        stopInput();
                    }
                }
            }

            process.terminate();

            var isErrorHappened = process.isErrorHappened();

            if (StageTest.getCurrTestRun().getErrorInTest() != null) {
                machine.setState(EXCEPTION_THROWN);

            } else if (isErrorHappened) {
                StageTest.getCurrTestRun().setErrorInTest(
                    new ExceptionWithFeedback(process.getStderr(), null));
                machine.setState(EXCEPTION_THROWN);

            } else {
                machine.setState(FINISHED);
            }

        } finally {
            FileUtils.chdir(oldWorkingDirectory);
        }
    }

    private boolean waitIfTerminated() {
        return tryManyTimes(100, 10,
            () -> process.isFinished(false));
    }

    @Override
    protected final void launch(String... args) {
        group = new ThreadGroup(this.toString());

        SystemHandler.installHandler(this, () ->
            TestingSecurityManager.getTestingGroup() == group);

        thread = new Thread(group, () -> handleProcess(List.of(args)));
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected final void terminate() {
        continueExecuting = false;
        process.terminate();
        while (!isFinished()) {
            if (isWaitingInput()) {
                machine.setState(RUNNING);
            }
            sleep(1);
        }
    }

    @Override
    public final void tearDown() {
        String oldWorkingDirectory = FileUtils.cwd();
        FileUtils.chdir(runnable.getFolder());

        try {
            cleanup();
        } catch (Throwable ignored) { }

        compiled = false;
        FileUtils.chdir(oldWorkingDirectory);
    }

    @Override
    public String toString() {
        return runnable.getFile().getName();
    }
}
