package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class TestCommandLineArgumentsFailedDynamicMethod5Main {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

class TestCommandLineArgumentsFailedDynamicMethod5Main2 {
    public static void main(String[] args) {

    }
}

public class TestCommandLineArgumentsFailedDynamicMethod5 extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Arguments for Main2: --second main\n" +
        "Arguments for Main: -in 123 -out 234\n" +
        "\n" +
        "4\n" +
        "-in\n" +
        "123\n" +
        "-out\n" +
        "234";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr2 = new TestedProgram(TestCommandLineArgumentsFailedDynamicMethod5Main2.class);
        pr2.start("--second", "main");

        TestedProgram pr = new TestedProgram(TestCommandLineArgumentsFailedDynamicMethod5Main.class);
        pr.start("-in", "123", "-out", "234");

        return new CheckResult(false, "");
    }
}
