package outcomes.test_with_long_output;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.NotContainMessage;
import outcomes.base.UserErrorTest;

class WrongOutputWithLongOutputMain {
    public static void main(String[] args) {
        for (int i = 0; i < 600; i++) {
            System.out.println("This is line number " + i);
        }
    }
}

public class WrongOutputWithLongOutput extends UserErrorTest {

    @NotContainMessage
    String[] lines = new String[]{
        "This is line number 12",
        "This is line number 86",
        "This is line number 241",
        "This is line number 335",
    };

    @DynamicTest
    CheckResult testOutput() {
        TestedProgram testedProgram = new TestedProgram(WrongOutputWithLongOutputMain.class);
        testedProgram.start();
        return CheckResult.wrong("");
    }
}

