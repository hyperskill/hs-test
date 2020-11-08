package outcomes.feedback_on_exception;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class FeedbackOnExceptionTest5Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(0 / 0);
    }
}

public class FeedbackOnExceptionTest5 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Exception in test #1\n" +
        "\n" +
        "BASE EX thrown\n" +
        "\n" +
        "java.lang.ArithmeticException";

    public FeedbackOnExceptionTest5() {
        super(FeedbackOnExceptionTest5Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .feedbackOnException(
                    IOException.class,
                    "IOEX thrown"
                )
                .feedbackOnException(
                    Exception.class,
                    "BASE EX thrown"
                )
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
