package org.hyperskill.hstest.common;

import org.hyperskill.hstest.dynamic.output.ColoredOutput;
import org.hyperskill.hstest.testcase.Process;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hyperskill.hstest.common.Utils.sleep;

public final class ProcessUtils {

    private ProcessUtils() { }

    public static ExecutorService newDaemonThreadPool(int poolSize) {
        return newDaemonThreadPool(poolSize, Thread.currentThread().getThreadGroup());
    }

    public static ExecutorService newDaemonThreadPool(int poolSize, ThreadGroup group) {
        return Executors.newFixedThreadPool(poolSize, r -> {
            Thread t = new Thread(group, r, group.getName(), 0);
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            t.setDaemon(true);
            return t;
        });
    }

    public static ExecutorService startThreads(List<Process> processes) {
        int poolSize = processes.size();
        if (poolSize == 0) {
            return null;
        }
        ExecutorService executor = newDaemonThreadPool(poolSize);
        for (Process process : processes) {
            process.start();
            executor.submit(process);
            while (!process.isStarted()) {
                int smallSleepTime = 10;
                sleep(smallSleepTime);
            }
        }
        return executor;
    }

    public static void stopThreads(List<Process> processes, ExecutorService executor) {
        if (executor == null) {
            return;
        }
        try {
            for (Process process : processes) {
                process.stop();
            }
            executor.shutdown();
            int sleepAwaitTime = 100;
            int sleepAwaitTimeLong = sleepAwaitTime * 10;
            boolean terminated = executor.awaitTermination(sleepAwaitTime, TimeUnit.MILLISECONDS);
            if (!terminated) {
                executor.shutdownNow();
                terminated = executor.awaitTermination(sleepAwaitTimeLong, TimeUnit.MILLISECONDS);
                if (!terminated) {
                    SystemOutHandler.getRealOut().println(
                        ColoredOutput.RED_BOLD + "SOME PROCESSES ARE NOT TERMINATED" + ColoredOutput.RESET
                    );
                }
            }
            for (int i = 1; i <= processes.size(); i++) {
                Process process = processes.get(i - 1);
                if (!process.isStopped()) {
                    SystemOutHandler.getRealOut().println(
                        ColoredOutput.RED_BOLD + "PROCESS #" + i + " IS NOT TERMINATED" + ColoredOutput.RESET
                    );
                }
            }
        } catch (InterruptedException ex) {
            // ignored
        }
    }

}
