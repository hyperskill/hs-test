package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestCommandLineArgumentsFailedDynamicMethod2 extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Arguments: -in 123 -out 234";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(Main2.class);
        pr.start("-in", "123", "-out", "234");
        return new CheckResult(false, "");
    }
}
