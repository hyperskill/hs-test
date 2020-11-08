package outcomes.number_format_exception;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.math.BigInteger;

class NumberFormatExceptionInTests8Main {
    public static void main(String[] args) {
        System.out.print("");
    }
}

public class NumberFormatExceptionInTests8 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot parse number\n" +
        "\n" +
        "java.lang.NumberFormatException: Zero length BigInteger";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(NumberFormatExceptionInTests8Main.class);
        BigInteger number = new BigInteger(main.start());
        return CheckResult.correct();
    }
}
