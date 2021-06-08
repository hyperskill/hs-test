package outcomes.long_error_output;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.NotContainMessage;
import outcomes.base.UserErrorTest;

class TestLabelOnNotEnoughLongErrorOutputMain {
    public static final int TOTAL_LINES = 250;

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_LINES; i++) {
            System.err.println("A " + i + " line");
        }
    }
}

public class TestLabelOnNotEnoughLongErrorOutput extends UserErrorTest<String> {

    @NotContainMessage
    String label = "last 250 lines of output are shown";

    @ContainsMessage
    static String[] correctLines = new String[250];

    static {
        int lineNumber = 0;

        for (int i = 0; i < correctLines.length; i++) {
            correctLines[i] = "A " + lineNumber++ + " line";
        }
    }

    @DynamicTest
    CheckResult testOutput() {
        TestedProgram testedProgram = new TestedProgram(TestLabelOnNotEnoughLongErrorOutputMain.class);
        testedProgram.start();
        return CheckResult.wrong("");
    }
}
