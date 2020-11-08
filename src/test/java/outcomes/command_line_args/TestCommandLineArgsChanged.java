package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestCommandLineArgsChanged extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Arguments: 123 234 345";

    @DynamicTestingMethod
    public CheckResult test() {
        TestedProgram main = new TestedProgram(Main3.class);
        main.start("123", "234", "345");
        return CheckResult.wrong("");
    }
}
