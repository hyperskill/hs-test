package outcomes.feedback_on_exception;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class FeedbackOnExceptionTestedProgramTest1Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(1 / 0);
    }
}

public class FeedbackOnExceptionTestedProgramTest1 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",

        "Do not divide by zero!\n" +
        "\n" +
        "java.lang.ArithmeticException: / by zero"
    };

    @DynamicTest
    CheckResult test() {
        TestedProgram pr = new TestedProgram(FeedbackOnExceptionTestedProgramTest1Main.class);
        pr.feedbackOnException(ArithmeticException.class, "Do not divide by zero!");
        pr.start();
        return CheckResult.correct();
    }
}
