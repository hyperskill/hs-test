package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Collections;
import java.util.List;

public class TestCommandLineArgumentsFailedDynamicTesting extends UserErrorTest<String> {

    @ContainsMessage
    String s =
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
        return Collections.singletonList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(Main.class);
                pr.start("-in", "123", "-out", "234");
                return new CheckResult(false, "");
            })
        );
    }
}
