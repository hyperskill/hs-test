package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class TestCommandLineArgumentsFailedDynamicMethod2Main {
    public static void main(String[] args) {

    }
}

public class TestCommandLineArgumentsFailedDynamicMethod2 extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Arguments: -in 123 -out 234";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestCommandLineArgumentsFailedDynamicMethod2Main.class);
        pr.start("-in", "123", "-out", "234");
        return new CheckResult(false, "");
    }
}
