package outcomes.separate_package.two_mains_6;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class TwoMains6Main1 {
    public static void main(String[] args) {
        System.out.print("Main1");
    }
}

class TwoMains6Main2 {
    public static void main(String[] args) {
        System.out.print("Main2");
    }
}

public class TwoMains6 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "There are 2 classes with main method in package \"outcomes.separate_package.two_mains_6\": " +
            "outcomes.separate_package.two_mains_6.TwoMains6Main1, " +
            "outcomes.separate_package.two_mains_6.TwoMains6Main2.\n" +
        "Leave only one of them to be executed.";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram();
        return CheckResult.correct();
    }

}
