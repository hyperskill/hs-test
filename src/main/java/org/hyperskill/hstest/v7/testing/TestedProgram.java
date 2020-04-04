package org.hyperskill.hstest.v7.testing;

import org.hyperskill.hstest.v7.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.v7.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.v7.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.v7.exception.outcomes.FatalError;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Future;

import static org.hyperskill.hstest.v7.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.v7.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.v7.exception.FailureHandler.getUserException;

public class TestedProgram {

    private enum ProgramState {
        NOT_STARTED, WAITING, RUNNING, FINISHED
    }

    private final StateMachine<ProgramState> machine =
        new StateMachine<>(ProgramState.NOT_STARTED);

    private volatile String output;
    private volatile String input;

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
            machine.waitState(ProgramState.RUNNING);
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

    public String start(String... args) {
        if (machine.getState() != ProgramState.NOT_STARTED) {
            throw new IllegalStateException("Cannot start the program twice");
        }
        machine.setState(ProgramState.WAITING);
        SystemInHandler.setDynamicInputFunc(output -> {
            this.output = output;
            machine.setAndWait(ProgramState.WAITING, ProgramState.RUNNING);
            return this.input;
        });
        runningProgram = newDaemonThreadPool(1).submit(() -> {
            invokeMain(args);
            machine.setState(ProgramState.FINISHED);
            return null;
        });
        return execute("");
    }

    public String execute(String input) {
        if (machine.getState() != ProgramState.WAITING) {
            throw new IllegalStateException("Cannot execute the program " +
                "that isn't waiting to be executed " +
                "(it isn't started or is running or has already finished)");
        }
        this.input = input;
        machine.setAndWait(ProgramState.RUNNING);
        return this.output;
    }

    public boolean isFinished() {
        return machine.getState() == ProgramState.FINISHED;
    }
}
