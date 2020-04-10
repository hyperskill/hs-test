package org.hyperskill.hstest.v7.outcomes;

public class WrongAnswerOutcome extends Outcome {

    public WrongAnswerOutcome(int testNum, String feedback) {
        super(testNum, feedback, "");
    }

    @Override
    protected String getType() {
        return "Wrong answer";
    }
}
