package org.hyperskill.hstest.v7.exception.outcomes;

public class ExceptionWithFeedback extends Exception {

    private Throwable realException;
    private String errorText;

    public ExceptionWithFeedback(String errorText, Throwable realException) {
        super(errorText);
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
