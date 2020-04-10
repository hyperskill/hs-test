package org.hyperskill.hstest.exception.outcomes;

public class FatalError extends OutcomeError {

    private final String errorText;
    private final Throwable cause;

    public FatalError(String errorText) {
        this(errorText, null);
    }

    public FatalError(String errorText, Throwable cause) {
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
