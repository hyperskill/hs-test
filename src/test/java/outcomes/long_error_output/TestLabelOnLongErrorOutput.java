package outcomes.long_error_output;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.NotContainMessage;
import outcomes.base.UserErrorTest;

class TestLabelOnLongErrorOutputMain {
    public static final int TOTAL_LINES = 251;

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_LINES; i++) {
            System.err.println("A " + i + " line");
        }
    }
}

public class TestLabelOnLongErrorOutput extends UserErrorTest<String> {

    @ContainsMessage
    String label = "[last 250 lines of output are shown, " + (TestLabelOnLongErrorOutputMain.TOTAL_LINES - 250) + " skipped]";

    @ContainsMessage
    static String[] correctLines = new String[250];

    @NotContainMessage
    static String[] wrongLines = new String[TestLabelOnLongErrorOutputMain.TOTAL_LINES - correctLines.length];

    @ContainsMessage
    String m = "stderr:\n" +
        "[last 250 lines of output are shown, 1 skipped]\n" +
        "A 1 line";

    static {
        int lineNumber = 0;

        for (int i = 0; i < wrongLines.length; i++) {
            wrongLines[i] = "A " + lineNumber++ + " line";
        }

        for (int i = 0; i < correctLines.length; i++) {
            correctLines[i] = "A " + lineNumber++ + " line";
        }
    }

    @DynamicTest
    CheckResult testOutput() {
        TestedProgram testedProgram = new TestedProgram(TestLabelOnLongErrorOutputMain.class);
        testedProgram.start();
        return CheckResult.wrong("");
    }
}
