package outcomes.feedback_on_exception;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class FeedbackOnExceptionTest2Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        throw new IOException();
    }
}

public class FeedbackOnExceptionTest2 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",

        "IOEX thrown\n" +
        "\n" +
        "java.io.IOException"
    };

    public FeedbackOnExceptionTest2() {
        super(FeedbackOnExceptionTest2Main.class);
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
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
