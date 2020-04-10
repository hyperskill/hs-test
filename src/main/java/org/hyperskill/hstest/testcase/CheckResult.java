package org.hyperskill.hstest.testcase;

public class CheckResult {

    public static CheckResult correct() {
        return new CheckResult(true, "");
    }

    public static CheckResult wrong(String feedback) {
        return new CheckResult(false, feedback);
    }

    private final boolean isCorrect;
    private final String feedback;

    @Deprecated
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

    public String getFeedback() {
        return feedback;
    }
}
