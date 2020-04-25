package org.hyperskill.hstest.exception.outcomes;

public class ErrorWithFeedback extends OutcomeError {
    private final String errorText;

    public ErrorWithFeedback(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }
}
