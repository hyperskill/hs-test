package org.hyperskill.hstest.v7.outcomes;

public class PresentationErrorOutcome extends Outcome {

    public PresentationErrorOutcome(int testNum, String feedback) {
        super(testNum, feedback, "");
    }

    @Override
    protected String getType() {
        return "Presentation error";
    }
}
