package outcomes.number_format_exception;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

class NumberFormatExceptionInCheck7Main {
    public static void main(String[] args) {
        System.out.println("qwe");
    }
}

public class NumberFormatExceptionInCheck7 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot parse Integer from the output part \"qwe\"";

    public NumberFormatExceptionInCheck7() {
        super(NumberFormatExceptionInCheck7Main.class);
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
