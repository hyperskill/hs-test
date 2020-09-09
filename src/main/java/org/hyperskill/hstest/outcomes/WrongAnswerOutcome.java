package org.hyperskill.hstest.outcomes;

import org.hyperskill.hstest.exception.outcomes.WrongAnswer;

public class WrongAnswerOutcome extends Outcome {

    public WrongAnswerOutcome(int testNum, WrongAnswer ex) {
        super(testNum, ex.getFeedbackText(), "");
    }

    @Override
    protected String getType() {
        return "Wrong answer";
    }
}
