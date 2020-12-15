package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class TestCommandLineArgumentsFailedDynamicMethodMain {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

public class TestCommandLineArgumentsFailedDynamicMethod extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Arguments: -in 123 -out 234\n" +
        "\n" +
        "4\n" +
        "-in\n" +
        "123\n" +
        "-out\n" +
        "234";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestCommandLineArgumentsFailedDynamicMethodMain.class);
        pr.start("-in", "123", "-out", "234");
        return new CheckResult(false, "");
    }
}
