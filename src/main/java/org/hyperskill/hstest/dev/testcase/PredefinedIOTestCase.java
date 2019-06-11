package org.hyperskill.hstest.dev.testcase;

public class PredefinedIOTestCase extends TestCase<String> {

    private String feedback = "";

    public PredefinedIOTestCase(String input, String output) {
        setInput(input);
        setAttach(output);
        setCheckFunc(this::customCheck);
    }

    public PredefinedIOTestCase setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    private CheckResult customCheck(String output, String attach) {
        boolean isCorrect = output.trim().equals(attach.trim());
        return new CheckResult(isCorrect, feedback);
    }
}
