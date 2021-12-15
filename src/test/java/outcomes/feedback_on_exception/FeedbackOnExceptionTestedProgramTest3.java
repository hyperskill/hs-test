package outcomes.feedback_on_exception;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.io.IOException;

class FeedbackOnExceptionTestedProgramTest3Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");
        throw new Exception();
    }
}

public class FeedbackOnExceptionTestedProgramTest3 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Exception in test #1\n" +
        "\n" +
        "java.lang.Exception";

    @DynamicTest
    CheckResult test() {
        TestedProgram pr = new TestedProgram(FeedbackOnExceptionTestedProgramTest3Main.class);
        pr.feedbackOnException(ArithmeticException.class, "Do not divide by zero!");
        pr.feedbackOnException(IOException.class, "IOEX thrown");
        pr.start();
        return CheckResult.correct();
    }
}
