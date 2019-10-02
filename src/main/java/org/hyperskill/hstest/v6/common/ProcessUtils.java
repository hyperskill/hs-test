package org.hyperskill.hstest.v6.common;

import org.hyperskill.hstest.v6.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v6.testcase.Process;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hyperskill.hstest.v6.common.Utils.sleep;
import static org.hyperskill.hstest.v6.dynamic.output.ColoredOutput.RED_BOLD;
import static org.hyperskill.hstest.v6.dynamic.output.ColoredOutput.RESET;

public class ProcessUtils {

    private ProcessUtils() {}

    public static ExecutorService startThreads(List<Process> processes) {
        int poolSize = processes.size();
        if (poolSize == 0) {
            return null;
        }
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for (Process process : processes) {
            process.start();
            executor.submit(process);
            while (!process.isStarted()) {
                sleep(10);
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
            boolean terminated = executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            if (!terminated) {
                executor.shutdownNow();
                terminated = executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
                if (!terminated) {
                    SystemOutHandler.getRealOut().println(
                        RED_BOLD + "SOME PROCESSES ARE NOT TERMINATED" + RESET
                    );
                }
            }
            for (int i = 1; i <= processes.size(); i++) {
                Process process = processes.get(i - 1);
                if (!process.isStopped()) {
                    SystemOutHandler.getRealOut().println(
                        RED_BOLD + "PROCESS #" + i + " IS NOT TERMINATED" + RESET
                    );
                }
            }
        } catch (InterruptedException ex) {
            // ignored
        }
    }

}
