package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.testing.TestedProgramFinishedEarly;
import org.hyperskill.hstest.v7.exception.testing.TestedProgramThrewException;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.exception.testing.TimeLimitException;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestRun;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hyperskill.hstest.v7.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.v7.testcase.CheckResult.correct;
import static org.hyperskill.hstest.v7.testcase.CheckResult.wrong;

public class AsyncMainMethodRunner implements TestRunner {

    private CheckResult runMain(TestRun testRun) {
        TestCase<?> testCase = testRun.getTestCase();
        int timeLimit = testCase.getTimeLimit();

        ExecutorService executorService = newDaemonThreadPool(1);
        Future<CheckResult> future = executorService
            .submit(() -> {
                try {
                    CheckResult result = testCase.getDynamicTesting().handle();
                    testRun.stopTestedPrograms();
                    return result;
                } catch (TestedProgramThrewException | TestedProgramFinishedEarly ignored) {
                    return null;
                }
            });

        try {
            if (timeLimit <= 0) {
                return future.get();
            } else {
                return future.get(timeLimit, TimeUnit.MILLISECONDS);
            }
        } catch (TimeoutException ex) {
            testRun.setErrorInTest(new TimeLimitException(timeLimit));
        } catch (ExecutionException ex) {
            testRun.setErrorInTest(ex.getCause());
        } catch (Throwable ex) {
            testRun.setErrorInTest(ex);
        } finally {
            executorService.shutdownNow();
        }

        return null;
    }

    @Override
    public <T> CheckResult test(TestRun testRun) {
        TestCase<T> testCase = (TestCase<T>) testRun.getTestCase();

        CheckResult result = runMain(testRun);

        if (result == null) {
            Throwable error = testRun.getErrorInTest();

            if (error == null) {
                try {
                    return testCase.getCheckFunc().apply(
                        SystemOutHandler.getOutput(), testCase.getAttach());
                } catch (Throwable ex) {
                    error = ex;
                    testRun.setErrorInTest(error);
                }
            }

            if (error instanceof TestPassed) {
                return correct();
            } else if (error instanceof WrongAnswer) {
                return wrong(((WrongAnswer) error).getFeedbackText());
            } else {
                return null;
            }
        }

        return result;
    }
}
