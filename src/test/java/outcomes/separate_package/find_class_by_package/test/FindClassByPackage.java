package outcomes.separate_package.find_class_by_package.test;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

public class FindClassByPackage extends StageTest {

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram(
                "outcomes.separate_package.find_class_by_package.package_with_class");
        return new CheckResult(main.start().equals("Class by package"), "");
    }

}
