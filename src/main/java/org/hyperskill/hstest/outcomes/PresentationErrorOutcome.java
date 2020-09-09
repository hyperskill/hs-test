package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.exception.outcomes.PresentationError;

public class PresentationErrorOutcome extends Outcome {

    public PresentationErrorOutcome(int testNum, PresentationError ex) {
        super(testNum, ex.getFeedbackText(), "");
    }

    @Override
    protected String getType() {
        return "Presentation error";
    }
}
