package org.hyperskill.hstest.exception.testing;

public class TimeLimitException extends Exception {
    private int timeLimitMs;

    public TimeLimitException(int timeLimitMs) {
        super();
        this.timeLimitMs = timeLimitMs;
    }

    public int getTimeLimitMs() {
        return timeLimitMs;
    }
}
