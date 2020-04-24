package org.hyperskill.hstest.testing;

import java.util.function.Supplier;

public class StateMachine<T> {
    private volatile T state;

    public StateMachine(T initialValue) {
        state = initialValue;
    }

    public T getState() {
        return state;
    }

    public synchronized void waitState(T waitingState) {
        waitAndSet(waitingState, waitingState);
    }

    public synchronized void waitAndSet(T waitingState, T newState) {
        waitSetWait(() -> state != waitingState, newState, () -> false);
    }

    public synchronized void setState(T newState) {
        setAndWait(newState, newState);
    }

    public synchronized void setAndWait(T newState, T waitingState) {
        waitSetWait(() -> false, newState, () -> state != waitingState);
    }

    public synchronized void setAndWait(T newState) {
        waitSetWait(() -> false, newState, () -> state == newState);
    }

    public synchronized void waitSetWait(Supplier<Boolean> checkWaitBefore, T newState,
                                          Supplier<Boolean> checkWaitAfter) {
        while (checkWaitBefore.get()) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }

        state = newState;
        notifyAll();

        while (checkWaitAfter.get()) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
    }
}
