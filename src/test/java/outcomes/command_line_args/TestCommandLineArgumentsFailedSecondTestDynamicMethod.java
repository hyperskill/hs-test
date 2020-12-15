package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class TestCommandLineArgumentsFailedSecondTestDynamicMethodMain {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

class TestCommandLineArgumentsFailedSecondTestDynamicMethodMain2 {
    public static void main(String[] args) {

    }
}

public class TestCommandLineArgumentsFailedSecondTestDynamicMethod extends UserErrorTest<Boolean> {

    public TestCommandLineArgumentsFailedSecondTestDynamicMethod() {
        super(TestCommandLineArgumentsFailedSecondTestDynamicMethodMain2.class);
    }

    @ContainsMessage
    String m =
        "Wrong answer in test #2\n" +
        "\n" +
        "Arguments: --second main";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(
            TestCommandLineArgumentsFailedSecondTestDynamicMethodMain.class);
        pr.start("-in", "123", "-out", "234");

        return new CheckResult(true, "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr2 = new TestedProgram(
            TestCommandLineArgumentsFailedSecondTestDynamicMethodMain2.class);
        pr2.start("--second", "main");

        return new CheckResult(false, "");
    }
}
