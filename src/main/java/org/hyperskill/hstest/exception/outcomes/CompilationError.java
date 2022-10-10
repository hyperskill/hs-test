package org.hyperskill.hstest.exception.outcomes;

public class CompilationError extends OutcomeError {
    private final String errorText;

    public CompilationError(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return errorText;
    }
}
