package org.hyperskill.hstest.exception.outcomes;

public class PresentationError extends OutcomeError {
    private final String feedbackText;

    public PresentationError(String feedbackText) {
        super(feedbackText);
        this.feedbackText = feedbackText;
    }

    public String getFeedbackText() {
        return feedbackText;
    }
}
