package outcomes.feedback_on_exception;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.io.IOException;

class FeedbackOnExceptionTestedProgramTest5Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(0 / 0);
    }
}

public class FeedbackOnExceptionTestedProgramTest5 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Exception in test #1\n" +
        "\n" +
        "BASE EX thrown\n" +
        "\n" +
        "java.lang.ArithmeticException";

    @DynamicTest
    CheckResult test() {
        TestedProgram pr = new TestedProgram(FeedbackOnExceptionTestedProgramTest5Main.class);
        pr.feedbackOnException(IOException.class, "IOEX thrown");
        pr.feedbackOnException(Exception.class, "BASE EX thrown");
        pr.start();
        return CheckResult.correct();
    }
}
