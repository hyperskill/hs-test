package outcomes.number_format_exception;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

class NumberFormatExceptionInCheck8Main {
    public static void main(String[] args) {
        System.out.print("");
    }
}

public class NumberFormatExceptionInCheck8 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot parse number\n" +
        "\n" +
        "java.lang.NumberFormatException: Zero length BigInteger";

    public NumberFormatExceptionInCheck8() {
        super(NumberFormatExceptionInCheck8Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(new TestCase());
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        new BigInteger(reply);
        return CheckResult.correct();
    }
}
