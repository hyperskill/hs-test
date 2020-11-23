package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestCommandLineArgumentsFailedSecondTestDynamicMethod extends UserErrorTest<Boolean> {

    public TestCommandLineArgumentsFailedSecondTestDynamicMethod() {
        super(Main2.class);
    }

    @ContainsMessage
    String m =
        "Wrong answer in test #2\n" +
        "\n" +
        "Arguments: --second main";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(Main.class);
        pr.start("-in", "123", "-out", "234");

        return new CheckResult(true, "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr2 = new TestedProgram(Main2.class);
        pr2.start("--second", "main");

        return new CheckResult(false, "");
    }
}
