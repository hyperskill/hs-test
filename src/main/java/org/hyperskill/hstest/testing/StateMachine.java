package org.hyperskill.hstest.testing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class StateMachine<T> {
    private volatile T state;
    private final Map<T, Set<T>> transitions = new HashMap<>();

    public StateMachine(T initialValue) {
        state = initialValue;
    }

    public void addTransition(T from, T to) {
        if (!transitions.containsKey(from)) {
            transitions.put(from, new HashSet<>());
        }
        transitions.get(from).add(to);
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

        if (state != newState && !transitions.get(state).contains(newState)) {
            throw new IllegalStateException(
                "Cannot transit from " + state + " to " + newState);
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
