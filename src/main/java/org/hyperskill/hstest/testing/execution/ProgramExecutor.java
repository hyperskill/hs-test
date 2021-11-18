package org.hyperskill.hstest.testing.execution;

import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.testing.TestedProgramFinishedEarly;
import org.hyperskill.hstest.exception.testing.TestedProgramThrewException;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.StateMachine;

import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.EXCEPTION_THROWN;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.FINISHED;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.NOT_STARTED;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.RUNNING;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.WAITING;

public abstract class ProgramExecutor {

    protected volatile String input;

    /**
     * States that tested program can be in
     * Initial state in NOT_STARTED,
     * State just before running the program is READY
     * End state is either EXCEPTION_THROWN or FINISHED
     * WAITING means the tested program waits for the input
     * RUNNING means the tested program is currently running
     */
    protected enum ProgramState {
        NOT_STARTED, WAITING, RUNNING, EXCEPTION_THROWN, FINISHED
    }

    protected final StateMachine<ProgramState> machine = new StateMachine<>(NOT_STARTED);

    {
        machine.addTransition(NOT_STARTED, RUNNING);

        machine.addTransition(WAITING, RUNNING);
        machine.addTransition(RUNNING, WAITING);

        machine.addTransition(RUNNING, EXCEPTION_THROWN);
        machine.addTransition(RUNNING, FINISHED);
    }

    private boolean inBackground = false;
    private boolean noMoreInput = false;
    private boolean returnOutputAfterExecution = true;

    protected abstract void launch(String... args);
    protected abstract void terminate();
    public abstract String getOutput();

    public final String start(String... args) {
        if (!machine.inState(NOT_STARTED)) {
            throw new UnexpectedError("Cannot start the program " + this + " twice");
        }

        launch(args);

        if (inBackground) {
            machine.waitNotState(NOT_STARTED);
            return "";
        }

        machine.waitNotStates(NOT_STARTED, RUNNING);
        return getExecutionOutput();
    }

    public final String execute(String input) {
        if (isFinished()) {
            StageTest.getCurrTestRun().setErrorInTest(new ErrorWithFeedback(
                "The program " + this + " has unexpectedly terminated.\n" +
                    "It finished execution too early, should continue running."));
            throw new TestedProgramFinishedEarly();
        }

        if (input == null) {
            stopInput();
            return "";
        }

        if (!isWaitingInput()) {
            throw new UnexpectedError(
                "Program " + this + " is not waiting for the input "
                    + "(state == \"" + machine.getState() + "\")");
        }

        if (noMoreInput) {
            throw new UnexpectedError(
                "Can't pass input to the program " + this + " - input was prohibited.");
        }

        this.input = input;
        if (inBackground) {
            machine.setState(RUNNING);
            return "";
        }

        // suspends thread while the program is executing,
        // waits for non-RUNNING state to be reached
        machine.setAndWait(RUNNING);
        return getExecutionOutput();
    }

    public final void stop() {
        noMoreInput = true;
        terminate();
    }

    private String getExecutionOutput() {
        if (machine.inState(EXCEPTION_THROWN)) {
            throw new TestedProgramThrewException();
        }
        return returnOutputAfterExecution ? getOutput() : "";
    }

    protected final String requestInput() {
        if (noMoreInput) {
            return null;
        }
        machine.setAndWait(WAITING, RUNNING);
        String inputLocal = input;
        input = null;
        return inputLocal;
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
     * @return true if the program is no longer able to execute any code,
     *         otherwise false
     */
    public boolean isFinished() {
        return machine.inState(FINISHED) || machine.inState(EXCEPTION_THROWN);
    }

    /**
     * After this method being called, every input request result in EOF being sent
     * to tested program without waiting for the proper input.
     * If tested program is waiting input, then EOF will also be sent.
     *
     * Note, that this cannot be undone and indicates the end of the input.
     */
    public final void stopInput() {
        inBackground = true;
        noMoreInput = true;
        if (isWaitingInput()) {
            machine.setState(RUNNING);
        }
    }

    /**
     * @return true if tested program waits for the input. Would be useful
     *         for the tested program that is executed in the background.
     */
    public boolean isWaitingInput() {
        return machine.inState(WAITING);
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
     * If program is already waiting for the input then returns immediately.
     * Otherwise waits for the input request and then returns.
     */
    public void stopBackground() {
        inBackground = false;
        machine.waitState(WAITING);
    }

    public boolean isInBackground() {
        return inBackground;
    }

    @Override
    public abstract String toString();
}
