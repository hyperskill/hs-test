package outcomes.long_output;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class LongOutputWithArgumentsMain {
    public static final int TOTAL_LINES = 251;

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_LINES; i++) {
            System.out.println("A " + i + " line");
        }
    }
}

public class LongOutputWithArguments extends UserErrorTest<String> {

    @ContainsMessage
    String line = "Arguments: -arg test\n\n" +
        "[last 250 lines of output are shown, 1 skipped]";

    @DynamicTest
    CheckResult testOutput() {
        TestedProgram testedProgram = new TestedProgram(LongOutputWithArgumentsMain.class);
        testedProgram.start("-arg", "test");
        return CheckResult.wrong("");
    }
}
