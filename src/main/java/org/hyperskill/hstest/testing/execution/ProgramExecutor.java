package org.hyperskill.hstest.testing.execution;

import org.hyperskill.hstest.testing.StateMachine;

public abstract class ProgramExecutor {

    protected volatile String input;

    /**
     * States that tested program can be in
     * Initial state in NOT_STARTED,
     * End state is either EXCEPTION_THROWN or FINISHED
     * WAITING means the tested program waits for the input
     * RUNNING means the tested program is currently running
     *
     * Only the following transitions are allowed:
     *
     * NOT_STARTED -> WAITING <-> RUNNING --> FINISHED
     *                                   `'-> EXCEPTION_THROWN
     */
    protected enum ProgramState {
        NOT_STARTED, WAITING, RUNNING, EXCEPTION_THROWN, FINISHED
    }

    protected final StateMachine<ProgramState> machine =
        new StateMachine<>(ProgramState.NOT_STARTED);

    {
        machine.addTransition(ProgramState.NOT_STARTED, ProgramState.WAITING);

        machine.addTransition(ProgramState.WAITING, ProgramState.RUNNING);
        machine.addTransition(ProgramState.RUNNING, ProgramState.WAITING);

        machine.addTransition(ProgramState.RUNNING, ProgramState.EXCEPTION_THROWN);
        machine.addTransition(ProgramState.RUNNING, ProgramState.FINISHED);
    }

    protected boolean inBackground = false;
    protected boolean noMoreInput = false;
    protected boolean returnOutputAfterExecution = true;

    public abstract void start(String... args);
    public abstract String execute(String input);
    public abstract void stop();

    public abstract String getOutput();

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
        return machine.getState() == ProgramState.FINISHED
            || machine.getState() == ProgramState.EXCEPTION_THROWN;
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
     * Starts tested program in the background
     * @param args arguments you want tested program to start with
     */
    public void startInBackground(String... args) {
        inBackground = true;
        start(args);
        execute("");
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
        machine.waitState(ProgramState.WAITING);
    }

    public boolean isInBackground() {
        return inBackground;
    }

    @Override
    public abstract String toString();
}
