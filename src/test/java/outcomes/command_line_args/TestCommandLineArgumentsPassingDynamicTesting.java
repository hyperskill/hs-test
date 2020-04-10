package outcomes.command_line_args;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;

public class TestCommandLineArgumentsPassingDynamicTesting extends StageTest<String> {
    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(Main.class);
                String out = pr.start("-in", "123", "-out", "234");
                return new CheckResult(out.equals("4\n-in\n123\n-out\n234\n"), "");
            }),

            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(Main.class);
                String out = pr.start("-in", "435", "-out", "567", "789");
                return new CheckResult(out.equals("5\n-in\n435\n-out\n567\n789\n"), "");
            })
        );
    }
}
