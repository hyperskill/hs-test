package outcomes.long_output;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.NotContainMessage;
import outcomes.base.UnexpectedErrorTest;

class LongOutputOnUnexpectedErrorMain {
    public static final int TOTAL_LINES = 600;

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_LINES; i++) {
            System.out.println("A " + i + " line");
        }
    }
}

public class LongOutputOnUnexpectedError extends UnexpectedErrorTest<String> {

    @ContainsMessage
    static String[] correctLines = new String[250];

    @NotContainMessage
    static String[] wrongLines = new String[LongOutputOnUnexpectedErrorMain.TOTAL_LINES - correctLines.length];

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
        TestedProgram testedProgram = new TestedProgram(LongOutputOnUnexpectedErrorMain.class);
        testedProgram.start();
        int a = 2 / 0;
        return CheckResult.wrong("");
    }
}

