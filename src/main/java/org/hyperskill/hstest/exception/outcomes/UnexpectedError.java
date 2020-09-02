package org.hyperskill.hstest.exception.outcomes;

public class UnexpectedError extends OutcomeError {

    private final String errorText;
    private final Throwable cause;

    public UnexpectedError(String errorText) {
        this(errorText, null);
    }

    public UnexpectedError(String errorText, Throwable cause) {
        super(errorText);
        this.errorText = errorText;
        this.cause = cause;
    }

    public String getErrorText() {
        return errorText;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
