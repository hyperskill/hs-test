package org.hyperskill.hstest.testcase;

public class SimpleTestCase extends TestCase<String> {
    public SimpleTestCase(String input, String output) {
        setInput(input);
        setAttach(output);
        setCheckFunc(this::customCheck);
    }

    private CheckResult customCheck(String reply, String expected) {
        boolean isCorrect = reply.trim().equals(expected.trim());
        return new CheckResult(isCorrect, "");
    }
}
