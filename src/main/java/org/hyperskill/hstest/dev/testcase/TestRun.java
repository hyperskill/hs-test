package org.hyperskill.hstest.dev.testcase;

public class TestRun {

    private int testNum;
    private TestCase testCase;

    private Throwable throwable;

    public TestRun(int testNum, TestCase testCase) {
        this.testNum = testNum;
        this.testCase = testCase;
    }

    public int getTestNum() {
        return testNum;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
