package org.hyperskill.hstest.exception.outcomes;

public class WrongAnswer extends OutcomeError {
    private final String feedbackText;

    public WrongAnswer(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
}
