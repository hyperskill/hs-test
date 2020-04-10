package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

public class TestCommandLineArgumentsPassingDynamicMethod extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(Main.class);
        String out = pr.start("-in", "123", "-out", "234");
        return new CheckResult(out.equals("4\n-in\n123\n-out\n234\n"), "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(Main.class);
        String out = pr.start("-in", "435", "-out", "567", "789");
        return new CheckResult(out.equals("5\n-in\n435\n-out\n567\n789\n"), "");
    }
}
