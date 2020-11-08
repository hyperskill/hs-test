package outcomes.feedback_on_exception;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class FeedbackOnExceptionTest4Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");
        throw new Exception();
    }
}

public class FeedbackOnExceptionTest4 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Exception in test #1\n" +
        "\n" +
        "BASE EX thrown\n" +
        "\n" +
        "java.lang.Exception";

    public FeedbackOnExceptionTest4() {
        super(FeedbackOnExceptionTest4Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .feedbackOnException(
                    ArithmeticException.class,
                    "Do not divide by zero!")
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
