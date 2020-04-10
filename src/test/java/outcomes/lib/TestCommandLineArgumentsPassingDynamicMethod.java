package outcomes.lib;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

class TestCommandLineArgumentsPassingDynamicMethodMain {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

public class TestCommandLineArgumentsPassingDynamicMethod extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(
            TestCommandLineArgumentsPassingDynamicMethodMain.class);
        String out = pr.start("-in", "123", "-out", "234");
        return new CheckResult(out.equals("4\n-in\n123\n-out\n234\n"), "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(
            TestCommandLineArgumentsPassingDynamicMethodMain.class);
        String out = pr.start("-in", "435", "-out", "567", "789");
        return new CheckResult(out.equals("5\n-in\n435\n-out\n567\n789\n"), "");
    }
}
