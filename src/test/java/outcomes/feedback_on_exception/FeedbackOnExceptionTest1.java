package outcomes.feedback_on_exception;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class FeedbackOnExceptionTest1Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(1 / 0);
    }
}

public class FeedbackOnExceptionTest1 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",

        "Do not divide by zero!\n" +
        "\n" +
        "java.lang.ArithmeticException: / by zero"
    };

    public FeedbackOnExceptionTest1() {
        super(FeedbackOnExceptionTest1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .feedbackOnException(
                    ArithmeticException.class,
                    "Do not divide by zero!")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
