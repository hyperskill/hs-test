package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

public class TestCommandLineArgumentsFailed2 extends UserErrorTest<String> {

    public TestCommandLineArgumentsFailed2() {
        super(Main2.class);
    }

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Arguments: -in 123 -out 234";

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
