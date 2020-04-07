package org.hyperskill.hstest.v7.exception.outcomes;

public class WrongAnswer extends OutcomeError {
    private final String feedbackText;

    public WrongAnswer(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
}
