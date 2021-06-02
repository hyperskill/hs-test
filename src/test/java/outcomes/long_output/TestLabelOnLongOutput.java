package outcomes.long_output;


import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.NotContainMessage;
import outcomes.base.UserErrorTest;

class TestLabelOnLongOutputMain {
    public static final int TOTAL_LINES = 251;

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_LINES; i++) {
            System.out.println("A " + i + " line");
        }
    }
}

public class TestLabelOnLongOutput extends UserErrorTest<String> {

    @ContainsMessage
    String label = "[last 250 lines of output are shown, " + (TestLabelOnLongOutputMain.TOTAL_LINES - 250) + " skipped]";

    @ContainsMessage
    static String[] correctLines = new String[250];

    @NotContainMessage
    static String[] wrongLines = new String[TestLabelOnLongOutputMain.TOTAL_LINES - correctLines.length];

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
        TestedProgram testedProgram = new TestedProgram(TestLabelOnLongOutputMain.class);
        testedProgram.start();
        return CheckResult.wrong("");
    }
}
