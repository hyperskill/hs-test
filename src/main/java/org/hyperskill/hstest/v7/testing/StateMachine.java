package org.hyperskill.hstest.v7.testing;

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
        while (state != waitingState) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
        state = newState;
        notifyAll();
    }

    public synchronized void setState(T newState) {
        setAndWait(newState, newState);
    }

    public synchronized void setAndWait(T newState, T waitingState) {
        state = newState;
        notifyAll();
        while (state != waitingState) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
    }

    public synchronized void setAndWait(T newState) {
        state = newState;
        notifyAll();
        while (state == newState) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
    }
}
