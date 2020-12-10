package org.hyperskill.hstest.testcase;

import lombok.Getter;

public class CheckResult {

    public static CheckResult correct() {
        return new CheckResult(true, "");
    }

    public static CheckResult wrong(String feedback) {
        return new CheckResult(false, feedback);
    }

    @Getter private final boolean correct;
    @Getter private final String feedback;

    @Deprecated
    public CheckResult(boolean isCorrect) {
        this(isCorrect, "");
    }

    public CheckResult(boolean correct, String feedback) {
        this.correct = correct;
        this.feedback = feedback;
    }
}
