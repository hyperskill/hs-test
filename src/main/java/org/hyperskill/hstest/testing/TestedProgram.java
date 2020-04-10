package org.hyperskill.hstest.testing;

import org.hyperskill.hstest.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.FatalError;
import org.hyperskill.hstest.exception.testing.TestedProgramFinishedEarly;
import org.hyperskill.hstest.exception.testing.TestedProgramThrewException;
import org.hyperskill.hstest.stage.StageTest;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.hyperskill.hstest.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.exception.FailureHandler.getUserException;

public class TestedProgram {

    private enum ProgramState {
        NOT_STARTED, WAITING, RUNNING, EXCEPTION_THROWN, FINISHED
    }

    private final StateMachine<ProgramState> machine =
        new StateMachine<>(ProgramState.NOT_STARTED);

    private volatile String output;
    private volatile String input;

    private boolean inBackground;

    private Method methodToInvoke;
    private final ThreadGroup group;
    private ExecutorService executor;
    private Future<?> task;

    private List<String> runArgs;
    private Class<?> runClass;

    public TestedProgram(Class<?> testedClass) {
        ClassLoader dcl = new DynamicClassLoader(testedClass);
        try {
            runClass = dcl.loadClass(testedClass.getName());
            methodToInvoke = getMainMethod(runClass);
            group = new ThreadGroup(runClass.getSimpleName());
            group.setDaemon(true);
        } catch (Exception ex) {
            throw new FatalError("Error initializing tested program", ex);
        }
    }

    private void invokeMain(String[] args) {
        try {
            machine.waitState(ProgramState.RUNNING);
            methodToInvoke.invoke(null, new Object[] { args });
            machine.setState(ProgramState.FINISHED);
        } catch (InvocationTargetException ex) {
            if (StageTest.getCurrTestRun().getErrorInTest() == null) {
                // CheckExitCalled is thrown in case of System.exit()
                // consider System.exit() like normal exit
                if (ex.getCause() instanceof CheckExitCalled) {
                    machine.setState(ProgramState.FINISHED);
                    return;
                }

                StageTest.getCurrTestRun().setErrorInTest(
                    new ExceptionWithFeedback("", getUserException(ex)));
            }
            machine.setState(ProgramState.EXCEPTION_THROWN);
        } catch (IllegalAccessException ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
            machine.setState(ProgramState.FINISHED);
        }
    }

    public void startInBackground(String... args) {
        start(true, args);
    }

    public String start(String... args) {
        return start(false, args);
    }

    public String start(boolean inBackground, String... args) {
        if (machine.getState() != ProgramState.NOT_STARTED) {
            throw new IllegalStateException("Cannot start the program twice");
        }

        this.inBackground = inBackground;
        this.runArgs = Arrays.asList(args);

        machine.setState(ProgramState.WAITING);
        SystemInHandler.setDynamicInputFunc(group, output -> {
            if (this.inBackground) {
                return null; // no waiting and no input, only EOF
            }
            this.output = output;
            machine.setAndWait(ProgramState.WAITING);
            String input = this.input;
            this.input = null;
            return input;
        });

        StageTest.getCurrTestRun().addTestedProgram(this);
        executor = newDaemonThreadPool(1, group);
        task = executor.submit(() -> invokeMain(args));
        return execute("");
    }

    public String execute(String input) {
        if (input == null || inBackground) {
            inBackground = true;
            machine.setState(ProgramState.RUNNING); // send "EOF" message and not wait for output
            return null;
        }

        if (machine.getState() == ProgramState.FINISHED) {
            StageTest.getCurrTestRun().setErrorInTest(
                new ErrorWithFeedback("The main method of the class "
                    + methodToInvoke.getDeclaringClass().getSimpleName()
                    + " has unexpectedly terminated"));
            throw new TestedProgramFinishedEarly();
        }

        if (machine.getState() != ProgramState.WAITING) {
            throw new IllegalStateException("Cannot execute the program " +
                "that isn't waiting to be executed " +
                "(it isn't started or is running or has already finished)");
        }

        this.input = input;
        machine.setAndWait(ProgramState.RUNNING);
        if (machine.getState() == ProgramState.EXCEPTION_THROWN) {
            throw new TestedProgramThrewException();
        }

        String output = this.output;
        if (machine.getState() == ProgramState.FINISHED) {
            output = SystemOutHandler.getPartialOutput(group);
        }

        this.output = null;
        return output;
    }

    public void stop() {
        executor.shutdownNow();
        task.cancel(true);
        synchronized (machine) {
            inBackground = true;
            while (!isFinished()) {
                this.input = null;
                machine.setAndWait(ProgramState.RUNNING);
            }
        }
    }

    public boolean isFinished() {
        return machine.getState() == ProgramState.FINISHED
            || machine.getState() == ProgramState.EXCEPTION_THROWN;
    }

    public List<String> getRunArgs() {
        return runArgs;
    }

    public Class<?> getRunClass() {
        return runClass;
    }
}
