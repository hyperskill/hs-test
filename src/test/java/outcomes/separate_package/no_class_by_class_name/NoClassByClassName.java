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
            "Cannot find either a package or a class named \"outcomes.separate_package.no_class_by_class_name.NonExistentClass\". " +
            "Check if you've created one of these.";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram(
                "outcomes.separate_package.no_class_by_class_name.NonExistentClass");
        return CheckResult.correct();
    }

}
