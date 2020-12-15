package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class TestCommandLineArgumentsFailedMain {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

public class TestCommandLineArgumentsFailed extends UserErrorTest<String> {

    public TestCommandLineArgumentsFailed() {
        super(TestCommandLineArgumentsFailedMain.class);
    }

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

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addArguments("-in", "123", "-out", "234")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(false, "");
    }
}
