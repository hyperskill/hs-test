package org.hyperskill.hstest.outcomes;

public class WrongAnswerOutcome extends Outcome {

    public WrongAnswerOutcome(int testNum, String feedback) {
        super(testNum, feedback, "");
    }

    @Override
    protected String getType() {
        return "Wrong answer";
    }
}
