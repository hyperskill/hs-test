package outcomes.lib;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class ExceptionInUserCode2Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello World");
            System.out.println(1 / 0);
        } catch (Exception ex) {
            System.out.println(1 / 0);
        }
    }
}

public class ExceptionInUserCode2 extends UserErrorTest {

    @ContainsMessage
    String m1 =
        "Exception in test #1\n" +
        "\n" +
        "java.lang.ArithmeticException: / by zero";

    @ContainsMessage
    String m2 =
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Hello World";

    public ExceptionInUserCode2() {
        super(ExceptionInUserCode2Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
