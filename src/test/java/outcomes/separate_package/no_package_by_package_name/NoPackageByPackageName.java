package outcomes.separate_package.no_package_by_package_name;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;


public class NoPackageByPackageName extends UserErrorTest {

    @ContainsMessage
    String m1 =
            "Error in test #1\n" +
                    "\n" +
                    "Cannot find a class with a main method.\n" +
                    "Check if you declared it as \"public static void main(String[] args)\".";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram(
                "outcomes.separate_package.non_existent_package");
        return CheckResult.correct();
    }

}
