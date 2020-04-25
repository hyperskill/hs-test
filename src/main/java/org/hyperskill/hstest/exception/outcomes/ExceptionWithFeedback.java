package org.hyperskill.hstest.exception.outcomes;

public class ExceptionWithFeedback extends OutcomeError {

    private final Throwable realException;
    private final String errorText;

    public ExceptionWithFeedback(String errorText, Throwable realException) {
        this.errorText = errorText;
        this.realException = realException;
    }

    public Throwable getRealException() {
        return realException;
    }

    public String getErrorText() {
        return errorText;
    }
}
