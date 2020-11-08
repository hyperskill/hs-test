package outcomes.number_format_exception;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class NumberFormatExceptionInTestsMain {
    public static void main(String[] args) {
        System.out.println("qwe");
    }
}

public class NumberFormatExceptionInTests extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot parse Integer from the output part \"qwe\"";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(NumberFormatExceptionInTestsMain.class);
        int number = Integer.parseInt(main.start());
        return CheckResult.correct();
    }
}
