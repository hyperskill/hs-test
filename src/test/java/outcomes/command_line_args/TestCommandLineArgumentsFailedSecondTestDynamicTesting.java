package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

public class TestCommandLineArgumentsFailedSecondTestDynamicTesting extends UserErrorTest<Boolean> {

    public TestCommandLineArgumentsFailedSecondTestDynamicTesting() {
        super(Main2.class);
    }

    @ContainsMessage
    String m =
        "Wrong answer in test #2\n" +
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
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr2 = new TestedProgram(Main2.class);
                pr2.start("--second", "main");

                return new CheckResult(true, "");
            }),

            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(Main.class);
                pr.start("-in", "123", "-out", "234");

                return new CheckResult(false, "");
            })
        );
    }
}
