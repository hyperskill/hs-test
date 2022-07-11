package outcomes.separate_package.no_class_by_class_name;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;


public class NoClassByClassName extends UserErrorTest<String> {

    @ContainsMessage
    String m =
            "Error in test #1\n" +
                    "\n" +
                    "Cannot find a class with a main method.\n" +
                    "Check if you declared it as \"public static void main(String[] args)\".";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram(
                "outcomes.separate_package.no_class_by_class_name.NonExistentClass");
        return CheckResult.correct();
    }

}
