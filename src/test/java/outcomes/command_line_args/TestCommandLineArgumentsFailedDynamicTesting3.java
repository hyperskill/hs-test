package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Collections;
import java.util.List;

class TestCommandLineArgumentsFailedDynamicTesting3Main {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

class TestCommandLineArgumentsFailedDynamicTesting3Main2 {
    public static void main(String[] args) {

    }
}

public class TestCommandLineArgumentsFailedDynamicTesting3 extends UserErrorTest<String> {

    @ContainsMessage
    String s =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Arguments for Main: -in 123 -out 234\n" +
        "Arguments for Main2: --second main\n" +
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
                TestedProgram pr = new TestedProgram(
                    TestCommandLineArgumentsFailedDynamicTesting3Main.class);
                pr.start("-in", "123", "-out", "234");

                TestedProgram pr2 = new TestedProgram(
                    TestCommandLineArgumentsFailedDynamicTesting3Main2.class);
                pr2.start("--second", "main");

                return new CheckResult(false, "");
            })
        );
    }
}
