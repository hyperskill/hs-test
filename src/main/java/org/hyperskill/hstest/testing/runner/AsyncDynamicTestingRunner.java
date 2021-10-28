package org.hyperskill.hstest.testing.runner;

import org.hyperskill.hstest.common.ProcessUtils;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.exception.testing.TestedProgramFinishedEarly;
import org.hyperskill.hstest.exception.testing.TestedProgramThrewException;
import org.hyperskill.hstest.exception.testing.TimeLimitException;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestRun;
import org.hyperskill.hstest.testing.execution.MainMethodExecutor;
import org.hyperskill.hstest.testing.execution.ProgramExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hyperskill.hstest.testing.ExecutionOptions.debugMode;

public class AsyncDynamicTestingRunner implements TestRunner {

    protected Class<? extends ProgramExecutor> executor;

    public AsyncDynamicTestingRunner() {
        this(MainMethodExecutor.class);
    }

    public AsyncDynamicTestingRunner(Class<? extends ProgramExecutor> executor) {
        this.executor = executor;
    }

    public Class<? extends ProgramExecutor> getExecutor() {
        return executor;
    }

    private CheckResult runMain(TestRun testRun) {
        TestCase<?> testCase = testRun.getTestCase();
        int timeLimit = testCase.getTimeLimit();

        ExecutorService executorService = ProcessUtils.newDaemonThreadPool(1);
        Future<CheckResult> future = executorService
            .submit(() -> {
                CheckResult result;
                try {
                    result = testCase.getDynamicTesting().handle();
                } catch (WrongAnswer wrongAnswer) {
                    result = CheckResult.wrong(wrongAnswer.getFeedbackText());
                } catch (TestPassed testPassed) {
                    result = CheckResult.correct();
                } catch (TestedProgramThrewException | TestedProgramFinishedEarly ignored) {
                    result = null;
                }
                if (result == null || result.isCorrect()) {
                    testRun.stopTestedPrograms();
                }
                return result;
            });

        try {
            if (timeLimit <= 0 || debugMode) {
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
            testRun.invalidateHandlers();
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
                        OutputHandler.getOutput(), testCase.getAttach());
                } catch (Throwable ex) {
                    error = ex;
                    testRun.setErrorInTest(error);
                }
            }

            if (error instanceof TestPassed) {
                return CheckResult.correct();
            } else if (error instanceof WrongAnswer) {
                return CheckResult.wrong(((WrongAnswer) error).getFeedbackText());
            } else {
                return null;
            }
        }

        return result;
    }

    @Override
    public void tearDown(TestCase<?> testCase) {
        for (var program : StageTest.getCurrTestRun().getTestedPrograms()) {
            program.getProgramExecutor().tearDown();
        }
    }
}
