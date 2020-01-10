package org.hyperskill.hstest.v6.testcase;

public class TestRun {

    private int testNum;
    private TestCase testCase;
    private boolean inputUsed = false;

    private Throwable errorInTest;

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

    public boolean isInputUsed() {
        return inputUsed;
    }

    public Throwable getErrorInTest() {
        return errorInTest;
    }

    public void setErrorInTest(Throwable errorInTest) {
        this.errorInTest = errorInTest;
    }

    public void setInputUsed() {
        inputUsed = true;
    }
}
