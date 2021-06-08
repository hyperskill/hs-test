package outcomes.long_error_output;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.NotContainMessage;
import outcomes.base.UserErrorTest;

class WrongOutputWithLongErrorOutputMain {
    public static final int TOTAL_LINES = 600;

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_LINES; i++) {
            System.err.println("A " + i + " line");
        }
    }
}

public class WrongOutputWithLongErrorOutput extends UserErrorTest<String> {

    @ContainsMessage
    static String[] correctLines = new String[250];

    @NotContainMessage
    static String[] wrongLines = new String[WrongOutputWithLongErrorOutputMain.TOTAL_LINES - correctLines.length];

    @ContainsMessage
    String message = "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "stderr:\n" +
        "[last 250 lines of output are shown, 350 skipped]\n" +
        "A 350 line";

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
        TestedProgram testedProgram = new TestedProgram(WrongOutputWithLongErrorOutputMain.class);
        testedProgram.start();
        return CheckResult.wrong("");
    }
}
