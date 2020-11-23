package outcomes.number_format_exception;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class NumberFormatExceptionInCheck5Main {
    public static void main(String[] args) {
        System.out.println("qwe");
    }
}

public class NumberFormatExceptionInCheck5 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
            "\n" +
            "Cannot parse Short from the output part \"qwe\"";

    public NumberFormatExceptionInCheck5() {
        super(NumberFormatExceptionInCheck5Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(new TestCase());
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        Short.parseShort(reply);
        return CheckResult.correct();
    }
}
