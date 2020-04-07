package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.dynamic.input.DynamicInput;
import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.testing.TestedProgramFinishedEarly;
import org.hyperskill.hstest.v7.exception.testing.TestedProgramThrewException;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.exception.outcomes.TimeLimitException;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hyperskill.hstest.v7.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.v7.testcase.CheckResult.correct;
import static org.hyperskill.hstest.v7.testcase.CheckResult.wrong;

public class AsyncMainMethodRunner implements TestRunner {

    private CheckResult runMain(TestCase<?> testCase) {
        int timeLimit = testCase.getTimeLimit();

        ExecutorService executorService = newDaemonThreadPool(1);
        Future<CheckResult> future = executorService
            .submit(() -> {
                try {
                    return testCase.getDynamicInput().handle();
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
            StageTest.getCurrTestRun().setErrorInTest(new TimeLimitException(timeLimit));
        } catch (Throwable ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
        } finally {
            executorService.shutdownNow();
        }

        return null;
    }

    @Override
    public <T> CheckResult test(TestCase<T> testCase) {
        if (testCase.getDynamicInput() == null) {
            DynamicInput converted = DynamicInput.toDynamicInput(
                testCase.getTestedClass(), testCase.getArgs(), testCase.getInputFuncs());
            testCase.setDynamicInput(converted);
        }

        CheckResult result = runMain(testCase);

        if (result == null) {
            Throwable error = StageTest.getCurrTestRun().getErrorInTest();

            if (error == null) {
                try {
                    return testCase.getCheckFunc().apply(
                        SystemOutHandler.getOutput(), testCase.getAttach());
                } catch (Throwable ex) {
                    error = ex;
                    StageTest.getCurrTestRun().setErrorInTest(error);
                }
            }

            if (error instanceof TestPassed) {
                return correct();
            } else if (error instanceof WrongAnswer) {
                return wrong(error.getMessage());
            } else {
                return null;
            }
        }

        return result;
    }
}
