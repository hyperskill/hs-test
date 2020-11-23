package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

public class TestCommandLineArgumentsFailedSecondTest extends UserErrorTest<Boolean> {

    public TestCommandLineArgumentsFailedSecondTest() {
        super(Main2.class);
    }

    @ContainsMessage
    String m =
        "Wrong answer in test #2\n" +
        "\n" +
        "Arguments: -in 123 -out 234";

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>()
                .addArguments("-in", "123", "-out", "234")
                .setAttach(true),

            new TestCase<Boolean>()
                .addArguments("-in", "123", "-out", "234")
                .setAttach(false)
        );
    }

    @Override
    public CheckResult check(String reply, Boolean attach) {
        return new CheckResult(attach, "");
    }
}
