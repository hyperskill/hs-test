package org.hyperskill.hstest.dev.testcase;

public class CheckResult {

    public static final CheckResult FALSE = new CheckResult(false);
    public static final CheckResult TRUE = new CheckResult(true);

    private boolean isCorrect;
    private String feedback;

    public CheckResult(boolean isCorrect) {
        this(isCorrect, "");
    }

    public CheckResult(boolean isCorrect, String feedback) {
        this.isCorrect = isCorrect;
        this.feedback = feedback;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
