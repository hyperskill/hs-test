package outcomes.number_format_exception;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.math.BigInteger;

class NumberFormatExceptionInTests7Main {
    public static void main(String[] args) {
        System.out.println("qwe");
    }
}

public class NumberFormatExceptionInTests7 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot parse Integer from the output part \"qwe\"";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(NumberFormatExceptionInTests7Main.class);
        BigInteger number = new BigInteger(main.start());
        return CheckResult.correct();
    }
}
