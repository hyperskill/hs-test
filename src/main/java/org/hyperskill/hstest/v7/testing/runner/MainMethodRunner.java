package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.v7.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.exception.testing.TimeLimitException;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestRun;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hyperskill.hstest.v7.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.v7.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.v7.exception.FailureHandler.getUserException;
import static org.hyperskill.hstest.v7.testcase.CheckResult.correct;
import static org.hyperskill.hstest.v7.testcase.CheckResult.wrong;

public class MainMethodRunner implements TestRunner {

    @Override
    public <T> CheckResult test(TestRun testRun) {
        TestCase<T> testCase = (TestCase<T>) testRun.getTestCase();

        SystemInHandler.setInputFuncs(testCase.getInputFuncs());

        runMain(testCase);
        String output = SystemOutHandler.getOutput();

        if (StageTest.getCurrTestRun().getErrorInTest() == null) {
            try {
                return testCase.getCheckFunc().apply(output, testCase.getAttach());
            } catch (WrongAnswer ex) {
                return wrong(ex.getFeedbackText());
            } catch (TestPassed ex) {
                return correct();
            }
        }

        return null;
    }

    private void runMain(TestCase<?> testCase) {
        int timeLimit = testCase.getTimeLimit();

        ExecutorService executorService = newDaemonThreadPool(1);

        Future<?> future = executorService.submit(() -> {
            invokeMain(testCase);
            return null;
        });

        try {
            if (timeLimit <= 0) {
                future.get();
            } else {
                future.get(timeLimit, TimeUnit.MILLISECONDS);
            }
        } catch (TimeoutException ex) {
            StageTest.getCurrTestRun().setErrorInTest(new TimeLimitException(timeLimit));
        } catch (Throwable ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
        } finally {
            executorService.shutdownNow();
        }
    }

    private void invokeMain(TestCase<?> testCase) {
        try {
            ClassLoader dcl = new DynamicClassLoader(testCase.getTestedClass());
            Class<?> reloaded = dcl.loadClass(testCase.getTestedClass().getName());
            getMainMethod(reloaded).invoke(testCase.getTestedObject(), new Object[] {
                testCase.getArgs().toArray(new String[0])
            });
        } catch (InvocationTargetException ex) {
            if (StageTest.getCurrTestRun().getErrorInTest() == null) {
                // CheckExitCalled is thrown in case of System.exit()
                // consider System.exit() like normal exit
                if (!(ex.getCause() instanceof CheckExitCalled)) {
                    StageTest.getCurrTestRun().setErrorInTest(
                        new ExceptionWithFeedback("", getUserException(ex)));
                }
            }
        } catch (Exception ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
        }
    }
}
