package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class TestCommandLineArgumentsFailedDynamicMethod3Main {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

class TestCommandLineArgumentsFailedDynamicMethod3Main2 {
    public static void main(String[] args) {

    }
}

public class TestCommandLineArgumentsFailedDynamicMethod3 extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Arguments for TestCommandLineArgumentsFailedDynamicMethod3Main: -in 123 -out 234\n" +
        "Arguments for TestCommandLineArgumentsFailedDynamicMethod3Main2: --second main\n" +
        "\n" +
        "4\n" +
        "-in\n" +
        "123\n" +
        "-out\n" +
        "234";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(
            TestCommandLineArgumentsFailedDynamicMethod3Main.class);
        pr.start("-in", "123", "-out", "234");

        TestedProgram pr2 = new TestedProgram(
            TestCommandLineArgumentsFailedDynamicMethod3Main2.class);
        pr2.start("--second", "main");

        return new CheckResult(false, "");
    }
}
