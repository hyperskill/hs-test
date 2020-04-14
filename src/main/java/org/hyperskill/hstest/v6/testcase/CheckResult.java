package org.hyperskill.hstest.v6.testcase;

public class CheckResult {

    public static final CheckResult FALSE = new CheckResult(false);
    public static final CheckResult TRUE = new CheckResult(true);

    public static CheckResult FALSE(String feedback) {
        return new CheckResult(false, feedback);
    }

    public static CheckResult TRUE(String feedback) {
        return new CheckResult(true, feedback);
    }

    private final boolean isCorrect;
    private final String feedback;

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
