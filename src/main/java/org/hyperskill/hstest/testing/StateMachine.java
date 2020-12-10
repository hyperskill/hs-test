package org.hyperskill.hstest.testing;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class StateMachine<T extends Enum<T>> {
    @Getter private volatile T state;
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

    public boolean inState(T state) {
        return getState() == state;
    }

    public synchronized void setAndWait(T newState) {
        setState(newState);
        waitNotState(newState);
    }

    public synchronized void setAndWait(T newState, T waitingState) {
        setState(newState);
        waitState(waitingState);
    }

    public synchronized void waitState(T waitingState) {
        waitWhile(() -> state != waitingState);
    }

    public synchronized void waitNotState(T stateToAvoid) {
        waitWhile(() -> state == stateToAvoid);
    }

    public final synchronized void waitNotStates(T... stateToAvoid) {
        waitWhile(() -> {
            for (T currState : stateToAvoid) {
                if (state == currState) {
                    return true;
                }
            }
            return false;
        });
    }

    private synchronized void waitWhile(Supplier<Boolean> checkWait) {
        while (checkWait.get()) {
            try {
                wait();
            } catch (InterruptedException ignored) { }
        }
    }

    public synchronized void setState(T newState) {
        if (!transitions.get(state).contains(newState)) {
            throw new IllegalStateException(
                "Cannot transit from " + state + " to " + newState);
        }
        state = newState;
        notifyAll();
    }
}
