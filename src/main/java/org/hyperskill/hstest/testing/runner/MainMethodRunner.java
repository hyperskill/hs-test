package org.hyperskill.hstest.testing.runner;

import org.hyperskill.hstest.common.ProcessUtils;
import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.dynamic.security.ProgramExited;
import org.hyperskill.hstest.exception.FailureHandler;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.exception.testing.TimeLimitException;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestRun;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Use AsyncMainMethodRunner instead
 */
@Deprecated
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
                return CheckResult.wrong(ex.getFeedbackText());
            } catch (TestPassed ex) {
                return CheckResult.correct();
            }
        }

        return null;
    }

    private void runMain(TestCase<?> testCase) {
        int timeLimit = testCase.getTimeLimit();

        ExecutorService executorService = ProcessUtils.newDaemonThreadPool(1);

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
            ReflectionUtils.getMainMethod(reloaded).invoke(testCase.getTestedObject(), new Object[] {
                testCase.getArgs().toArray(new String[0])
            });
        } catch (InvocationTargetException ex) {
            if (StageTest.getCurrTestRun().getErrorInTest() == null) {
                // CheckExitCalled is thrown in case of System.exit()
                // consider System.exit() like normal exit
                if (!(ex.getCause() instanceof ProgramExited)) {
                    StageTest.getCurrTestRun().setErrorInTest(
                        new ExceptionWithFeedback("", FailureHandler.getUserException(ex)));
                }
            }
        } catch (Exception ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
        }
    }
}
