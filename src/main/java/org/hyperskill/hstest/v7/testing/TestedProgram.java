package org.hyperskill.hstest.v7.testing;

import org.hyperskill.hstest.v7.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.v7.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.v7.exception.outcomes.FatalError;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hyperskill.hstest.v7.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.v7.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.v7.exception.FailureHandler.getUserException;

public class TestedProgram {

    private enum ProgramState {
        NOT_STARTED, RUNNING, FINISHED
    }

    private ProgramState state = ProgramState.NOT_STARTED;

    private Method methodToInvoke;
    private Future<?> runningProgram;

    public TestedProgram(Class<?> testedClass) {
        ClassLoader dcl = new DynamicClassLoader(testedClass);
        try {
            Class<?> reloaded = dcl.loadClass(testedClass.getName());
            methodToInvoke = getMainMethod(reloaded);
        } catch (Exception ex) {
            throw new FatalError("Cannot get main method of the tested class", ex);
        }
    }

    private void invokeMain(String[] args) {
        try {
            methodToInvoke.invoke(null, new Object[] { args });
        } catch (InvocationTargetException ex) {
            if (StageTest.getCurrTestRun().getErrorInTest() == null) {
                // CheckExitCalled is thrown in case of System.exit()
                // consider System.exit() like normal exit
                if (!(ex.getCause() instanceof CheckExitCalled)) {
                    StageTest.getCurrTestRun().setErrorInTest(
                        new ExceptionWithFeedback("", getUserException(ex)));
                }
            }
        } catch (IllegalAccessException ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
        }
    }

    private void start(String... args) {
        if (state != ProgramState.NOT_STARTED) {
            throw new IllegalStateException("Cannot start the program twice");
        }

        ExecutorService executorService = newDaemonThreadPool(1);

        runningProgram = executorService.submit(() -> {
            invokeMain(args);
            return null;
        });
    }

    public void execute() {
        if (state == ProgramState.NOT_STARTED) {
            start();
        }


    }

    public void execute(String... args) {
        if (state != ProgramState.NOT_STARTED) {
            throw new IllegalStateException("Cannot start the program twice");
        }
        start(args);
        execute();
    }
}
