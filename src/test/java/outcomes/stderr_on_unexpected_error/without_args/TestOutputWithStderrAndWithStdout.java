package outcomes.stderr_on_unexpected_error.without_args;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

class TestOutputWithStderrAndWithStderrMain {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println("User stdout output!");
        }

        for (int i = 0; i < 3; i++) {
            System.err.println("User stderr output!");
        }
    }
}

public class TestOutputWithStderrAndWithStdout extends UnexpectedErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "java.lang.ArithmeticException: / by zero",
        "Please find below the output of your program during this failed test.\n\n---\n\n" +
            "stdout:\nUser stdout output!\nUser stdout output!\nUser stdout output!\n\n" +
            "stderr:\nUser stderr output!\nUser stderr output!\nUser stderr output!"
    };

    @DynamicTest
    CheckResult test() {
        TestedProgram testedProgram = new TestedProgram(TestOutputWithStderrAndWithStderrMain.class);
        testedProgram.start();
        int a = 2 / 0;
        return CheckResult.correct();
    }

}
