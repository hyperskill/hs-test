package org.hyperskill.hstest;

public class CheckResult {

    public static final CheckResult FALSE = new CheckResult(false);
    public static final CheckResult TRUE = new CheckResult(true);

    public boolean isCorrect;
    public String feedback;

    public CheckResult(boolean isCorrect) {
        this(isCorrect, "");
    }

    public CheckResult(boolean isCorrect, String feedback) {
        this.isCorrect = isCorrect;
        this.feedback = feedback;
    }
}
