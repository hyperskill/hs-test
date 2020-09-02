package org.hyperskill.hstest.testing;

import org.hyperskill.hstest.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.testing.TestedProgramFinishedEarly;
import org.hyperskill.hstest.exception.testing.TestedProgramThrewException;
import org.hyperskill.hstest.stage.StageTest;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.hyperskill.hstest.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.exception.FailureHandler.getUserException;

/**
 * Class for running user program and asynchronously test it with input that can be generated while
 * the tested program is running.
 *
 * Supposed to be used inside DynamicTesting::handle method.
 *
 * The main feature is to "freeze" user thread while it's waiting for the input and generate appropriate
 * input based on current output.
 *
 * The main flow is:
 * 1. Create TestedProgram instance with the class, whose main method you want to run
 * 2. Start the test using TestedProgram::start
 * 4. The tested program will execute till it needs some input
 * 6. "Start" returns input that was obtained during tested program's execution
 * 7. Some testing code generates new input for the tested program
 * 8. Continue testing with the new input using TestedProgram::execute method
 * 9. The tested program will execute till it needs some input etc...
 */
public class TestedProgram {

    /**
     * States that tested program can be in
     * Initial state in NOT_STARTED,
     * End state is either EXCEPTION_THROWN or FINISHED
     * WAITING means the tested program waits for the input
     * RUNNING means teh tested program is currently running
     *
     * Only the following transitions are allowed:
     *
     * NOT_STARTED -> WAITING <-> RUNNING --> FINISHED
     *                                   `'-> EXCEPTION_THROWN
     */
    private enum ProgramState {
        NOT_STARTED, WAITING, RUNNING, EXCEPTION_THROWN, FINISHED
    }

    private final StateMachine<ProgramState> machine =
        new StateMachine<>(ProgramState.NOT_STARTED);

    {
        machine.addTransition(ProgramState.NOT_STARTED, ProgramState.WAITING);

        machine.addTransition(ProgramState.WAITING, ProgramState.RUNNING);
        machine.addTransition(ProgramState.RUNNING, ProgramState.WAITING);

        machine.addTransition(ProgramState.RUNNING, ProgramState.EXCEPTION_THROWN);
        machine.addTransition(ProgramState.RUNNING, ProgramState.FINISHED);
    }

    private volatile String input;

    private boolean inBackground = false;
    private boolean noMoreInput = false;

    private boolean returnOutputAfterExecution = true;

    private final Method methodToInvoke;
    private final ThreadGroup group;
    private ExecutorService executor;
    private Future<?> task;

    private List<String> runArgs;
    private final Class<?> runClass;

    public List<String> getRunArgs() {
        return runArgs;
    }

    public Class<?> getRunClass() {
        return runClass;
    }

    /**
     * Creates TestedProgram instance, but doesn't run the class
     * @param testedClass class, whose main method you want to test
     */
    public TestedProgram(Class<?> testedClass) {
        ClassLoader dcl = new DynamicClassLoader(testedClass);
        try {
            runClass = dcl.loadClass(testedClass.getName());
            methodToInvoke = getMainMethod(runClass);
            group = new ThreadGroup(runClass.getSimpleName());
            group.setDaemon(true);
        } catch (Exception ex) {
            throw new UnexpectedError("Error initializing tested program", ex);
        }
    }

    private String waitOutput(String input) {
        if (!isWaitingInput()) {
            throw new UnexpectedError(
                "Tested program is not waiting for the input " +
                "(state == \"" + machine.getState() + "\")");
        }

        if (noMoreInput) {
            throw new UnexpectedError(
                "Can't input to the tested program - input was prohibited.");
        }

        this.input = input;
        if (inBackground) {
            machine.setState(ProgramState.RUNNING);
            return "";
        }

        machine.setAndWait(ProgramState.RUNNING);
        if (machine.getState() == ProgramState.EXCEPTION_THROWN) {
            throw new TestedProgramThrewException();
        }
        return returnOutputAfterExecution ? getOutput() : "";
    }

    private String waitInput() {
        if (noMoreInput) {
            return null;
        }
        machine.setAndWait(ProgramState.WAITING, ProgramState.RUNNING);
        String inputLocal = input;
        input = null;
        return inputLocal;
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

    /**
     * Starts tested program in the background
     * @param args arguments you want tested program to start with
     */
    public void startInBackground(String... args) {
        inBackground = true;
        start(args);
    }

    /**
     * Starts tested program synchronously, so this method will block test execution
     * till tested program request an output.
     * @param args arguments you want tested program to start with
     * @return Output that tested program manages to print while executing the program.
     *         Returns an empty string if returnOutputAfterExecution is set to false.
     */
    public String start(String... args) {
        if (machine.getState() != ProgramState.NOT_STARTED) {
            throw new UnexpectedError("Cannot start the program twice");
        }

        this.runArgs = new ArrayList<>(Arrays.asList(args));

        machine.setState(ProgramState.WAITING);
        SystemInHandler.setDynamicInputFunc(group, this::waitInput);

        StageTest.getCurrTestRun().addTestedProgram(this);
        executor = newDaemonThreadPool(1, group);
        task = executor.submit(() -> invokeMain(args));
        return execute("");
    }

    /**
     * @param input input that needs to be sent to the tested program.
     * @return Output that tested program manages to print while executing the program
     *         with the given input.
     *         Returns an empty string in case of:
     *         1. returnOutputAfterExecution is set to false.
     *         2. The execution is done in the background.
     */
    public String execute(String input) {
        if (isFinished()) {
            StageTest.getCurrTestRun().setErrorInTest(
                new ErrorWithFeedback("The main method of the class "
                    + methodToInvoke.getDeclaringClass().getSimpleName()
                    + " has unexpectedly terminated"));
            throw new TestedProgramFinishedEarly();
        }

        if (input == null) {
            stopInput();
            return "";
        }

        return waitOutput(input);
    }

    /**
     * @return Output that tested program is managed to print since the last
     *         "start", "execute" or "getOutput" is invoked.
     *
     *         The TestedProgram class returns every line of the output only once.
     *         So, the concatenation of all the strings that were returned in
     *         "start", "execute", "getOutput" methods will be always equal
     *         to the whole output of the tested program.
     */
    public String getOutput() {
        return SystemOutHandler.getPartialOutput(group);
    }

    /**
     * Stops the tested program and waits it to be finished either by throwing an exception
     * or just by a plain return.
     */
    public void stop() {
        executor.shutdownNow();
        task.cancel(true);
        group.interrupt();
        synchronized (machine) {
            inBackground = true;
            while (!isFinished()) {
                this.input = null;
                machine.setAndWait(ProgramState.RUNNING);
            }
        }
    }

    /**
     * @return true if the tested program is no longer able to execute any code,
     *         otherwise false
     */
    public boolean isFinished() {
        return machine.getState() == ProgramState.FINISHED
            || machine.getState() == ProgramState.EXCEPTION_THROWN;
    }

    /**
     * If set to false, methods "execute" and "start" will no longer return output but return
     * just an empty string. In this case you can get the output only by "getOutput" method.
     *
     * If set to true, methods "execute" and "start" will return meaningful output.
     * It's default behavior.
     *
     * Notice, that the method "execute" will return an empty string if the program is running
     * in the background regardless of the value of returnOutputAfterExecution.
     */
    public void setReturnOutputAfterExecution(boolean value) {
        this.returnOutputAfterExecution = value;
    }

    /**
     * After this method being called, every input request result in EOF being sent
     * to tested program without waiting for the proper input.
     * If tested program is waiting input, then EOF also will be sent.
     *
     * Note, that this cannot be undone and indicates the end of the input.
     */
    public void stopInput() {
        inBackground = true;
        noMoreInput = true;
        if (isWaitingInput()) {
            machine.setState(ProgramState.RUNNING);
        }
    }

    /**
     * @return true if tested program waits for the input. Would be useful
     *         for the tested program that is executed in the background.
     */
    public boolean isWaitingInput() {
        return machine.getState() == ProgramState.WAITING;
    }

    /**
     * Moves tested program in background mode. Program still be waiting for the input.
     * You can still input data into tested program using "execute" method,
     * but this method return immediately, while tested program will continue to run.
     * Nothing happens if tested program is already in background mode.
     */
    public void goBackground() {
        inBackground = true;
    }

    /**
     * Moves tested program from background mode into plain sequential mode.
     * If tested program is already waiting for the input then returns immediately.
     * Otherwise waits for the input request and then returns.
     */
    public void stopBackground() {
        inBackground = false;
        machine.waitState(ProgramState.WAITING);
    }

    public boolean isInBackground() {
        return inBackground;
    }
}
