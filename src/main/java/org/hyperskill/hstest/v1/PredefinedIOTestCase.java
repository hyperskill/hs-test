package org.hyperskill.hstest.v1;

public class PredefinedIOTestCase extends TestCase<String> {

    public PredefinedIOTestCase() {
        super();
    }

    public PredefinedIOTestCase(String input, String output) {
        super(output, input);
    }

    public PredefinedIOTestCase setConsoleOutput(String output) {
        setClue(output);
        return this;
    }

}
