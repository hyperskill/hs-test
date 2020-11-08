package outcomes.number_format_exception;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class NumberFormatExceptionInTests4Main {
    public static void main(String[] args) {
        System.out.println("qwe");
    }
}

public class NumberFormatExceptionInTests4 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot parse Double from the output part \"qwe\"";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(NumberFormatExceptionInTests4Main.class);
        double number = Double.parseDouble(main.start());
        return CheckResult.correct();
    }
}
