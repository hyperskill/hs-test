package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Collections;
import java.util.List;

class TestCommandLineArgumentsFailedDynamicTesting5Main {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

class TestCommandLineArgumentsFailedDynamicTesting5Main2 {
    public static void main(String[] args) {

    }
}

public class TestCommandLineArgumentsFailedDynamicTesting5 extends UserErrorTest<String> {

    @ContainsMessage
    String s =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Arguments for TestCommandLineArgumentsFailedDynamicTesting5Main2: --second main\n" +
        "Arguments for TestCommandLineArgumentsFailedDynamicTesting5Main: -in 123 -out 234\n" +
        "\n" +
        "4\n" +
        "-in\n" +
        "123\n" +
        "-out\n" +
        "234";

    @Override
    public List<TestCase<String>> generate() {
        return Collections.singletonList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr2 = new TestedProgram(
                    TestCommandLineArgumentsFailedDynamicTesting5Main2.class);
                pr2.start("--second", "main");

                TestedProgram pr = new TestedProgram(
                    TestCommandLineArgumentsFailedDynamicTesting5Main.class);
                pr.start("-in", "123", "-out", "234");

                return new CheckResult(false, "");
            })
        );
    }
}
