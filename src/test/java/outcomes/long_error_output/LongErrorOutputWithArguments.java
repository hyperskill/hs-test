package outcomes.long_error_output;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class LongErrorOutputWithArgumentsMain {
    public static final int TOTAL_LINES = 251;

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_LINES; i++) {
            System.err.println("A " + i + " line");
        }
    }
}

public class LongErrorOutputWithArguments extends UserErrorTest<String> {
    @ContainsMessage
    String line = "Arguments: -arg test\n" +
        "\n" +
        "stderr:\n" +
        "[last 250 lines of output are shown, 1 skipped]";

    @DynamicTest
    CheckResult testOutput() {
        TestedProgram testedProgram = new TestedProgram(LongErrorOutputWithArgumentsMain.class);
        testedProgram.start("-arg", "test");
        return CheckResult.wrong("");
    }
}
