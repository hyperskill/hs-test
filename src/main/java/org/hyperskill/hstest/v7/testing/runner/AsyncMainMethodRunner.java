package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.v7.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.exception.outcomes.TimeLimitException;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.hyperskill.hstest.v7.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.v7.exception.FailureHandler.getUserException;
import static org.hyperskill.hstest.v7.testcase.CheckResult.correct;
import static org.hyperskill.hstest.v7.testcase.CheckResult.wrong;

public class AsyncMainMethodRunner implements TestRunner {

    TestCase<?> testCase = null;

    private void runMain(List<String> args, int timeLimit) {
        ExecutorService executorService = newSingleThreadExecutor(r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        });

        Future<?> future = executorService.submit(() -> {
            invokeMain(args);
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

    private void invokeMain(List<String> args) {
        try {
            ClassLoader dcl = new DynamicClassLoader(testCase.getTestedClass());
            Class<?> reloaded = dcl.loadClass(testCase.getTestedClass().getName());
            getMainMethod(reloaded).invoke(testCase.getTestedObject(), new Object[] {
                args.toArray(new String[0])
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

    @Override
    public <T> CheckResult test(TestCase<T> testCase) {
        this.testCase = testCase;

        SystemInHandler.setInputFuncs(testCase.getInputFuncs());
        SystemOutHandler.resetOutput();

        runMain(testCase.getArgs(), testCase.getTimeLimit());

        String output = SystemOutHandler.getOutput();

        if (StageTest.getCurrTestRun().getErrorInTest() == null) {
            try {
                return testCase.getCheckFunc().apply(output, testCase.getAttach());
            } catch (WrongAnswer ex) {
                return wrong(ex.getMessage());
            } catch (TestPassed ex) {
                return correct();
            }
        }

        return null;
    }
}
