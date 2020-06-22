package outcomes.command_line_args;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Collections;
import java.util.List;

public class TestCommandLineArgumentsFailedWithSpaces extends StageTest<String> {

    @Override
    public List<TestCase<String>> generate() {
        return Collections.singletonList(
                new TestCase<String>().setDynamicTesting(() -> {
                    TestedProgram pr = new TestedProgram(Main.class);
                    String out = pr.start("-spaces", "some argument with spaces", "-number", "234", "-onlySpaces", "      ");
                    return CheckResult.wrong("See arguments below:");
                })
        );
    }
}
