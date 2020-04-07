package org.hyperskill.hstest.v7.exception.outcomes;

public class ExceptionWithFeedback extends OutcomeError {

    private Throwable realException;
    private String errorText;

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
