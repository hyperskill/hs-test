package org.hyperskill.hstest.v7.exception.outcomes;

public class ErrorWithFeedback extends OutcomeError {
    private String errorText;

    public ErrorWithFeedback(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }
}
