package org.hyperskill.hstest.v3.testcase;

public class PredefinedIOTestCase extends TestCase<String> {

    public PredefinedIOTestCase() {
        super();
    }

    public PredefinedIOTestCase(String input, String output) {
        super();
        setInput(input);
        setAttach(output);
    }

}
