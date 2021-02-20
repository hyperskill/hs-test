package org.hyperskill.hstest.testing.expect.base.checker;

public class ArrayLengthChecker {
    public IntegerChecker lengthChecker;
    public String feedback;

    public ArrayLengthChecker(IntegerChecker checker, String feedback) {
        this.lengthChecker = checker;
        this.feedback = feedback;
    }
}
