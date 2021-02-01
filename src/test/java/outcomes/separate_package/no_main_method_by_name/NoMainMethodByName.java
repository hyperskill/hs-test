package outcomes.separate_package.no_main_method_by_name;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class NoMainMethodByNameMain {

}

public class NoMainMethodByName extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot find a class with a main method in package \"outcomes.separate_package.no_main_method_by_name\".\n" +
        "Check if you declared it as \"public static void main(String[] args)\".";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            "outcomes.separate_package.no_main_method_by_name.NoMainMethodByNameMain");
        return CheckResult.correct();
    }

}
