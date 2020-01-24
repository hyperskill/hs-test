package org.hyperskill.hstest.v7.testcase;


public class SimpleTestCase extends TestCase<String> {

    private String feedback = "";

    public SimpleTestCase(String input, String output) {
        setInput(input);
        setAttach(output);
        setCheckFunc(this::customCheck);
    }

    public SimpleTestCase setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    private CheckResult customCheck(String reply, String expected) {
        boolean isCorrect = reply.trim().equals(expected.trim());
        return new CheckResult(isCorrect, feedback);
    }
}
