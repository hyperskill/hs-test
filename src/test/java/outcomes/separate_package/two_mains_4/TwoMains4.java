package outcomes.separate_package.two_mains_4;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

class Main1 {
    public static void main(String[] args) {
        System.out.print("Main1");
    }
}

class Main2 {
    public static void main(String[] args) {
        System.out.print("Main2");
    }
}

public class TwoMains4 extends StageTest {

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram(
            "outcomes.separate_package.two_mains_4.Main1");
        return new CheckResult(main.start().equals("Main1"), "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram main = new TestedProgram(
            "outcomes.separate_package.two_mains_4.Main2");
        return new CheckResult(main.start().equals("Main2"), "");
    }

}
