package outcomes.separate_package.find_class_by_name;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

class Main1 {
    public static void main(String[] args) {
        System.out.print("Class by name");
    }
}

public class FindClassByName extends StageTest {

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram(
            "outcomes.separate_package.find_class_by_name.Main1");
        return new CheckResult(main.start().equals("Class by name"), "");
    }

}
