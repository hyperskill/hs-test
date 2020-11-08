package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Collections;
import java.util.List;

public class TestCommandLineArgumentsFailedDynamicTesting2 extends UserErrorTest<String> {

    @ContainsMessage
    String s =
        "Wrong answer in test #1\n" +
        "\n" +
        "Arguments: -in 123 -out 234";

    @Override
    public List<TestCase<String>> generate() {
        return Collections.singletonList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(Main2.class);
                pr.start("-in", "123", "-out", "234");
                return new CheckResult(false, "");
            })
        );
    }
}
