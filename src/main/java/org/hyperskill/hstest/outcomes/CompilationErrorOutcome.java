package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.exception.outcomes.CompilationError;

public class CompilationErrorOutcome extends Outcome {
    public CompilationErrorOutcome(CompilationError ex) {
        testNumber = -1;
        errorText = ex.getErrorText();
    }

    @Override
    protected String getType() {
        return "Compilation error";
    }
}
