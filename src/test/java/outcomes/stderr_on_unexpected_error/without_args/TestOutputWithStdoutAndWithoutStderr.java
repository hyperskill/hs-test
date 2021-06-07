package outcomes.stderr_on_unexpected_error.without_args;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

class TestOutputWithStdoutAndWithoutStderrMain {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println("User stdout output!");
        }

//        for (int i = 0; i < 3; i++) {
//            System.err.println("User stderr output!");
//        }
    }
}

public class TestOutputWithStdoutAndWithoutStderr extends UnexpectedErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "java.lang.ArithmeticException: / by zero",
        "Please find below the output of your program during this failed test.\n\n---\n\nUser stdout output!" +
            "\nUser stdout output!\nUser stdout output!"
    };

    @DynamicTest
    CheckResult test() {
        TestedProgram testedProgram = new TestedProgram(TestOutputWithStdoutAndWithoutStderrMain.class);
        testedProgram.start();
        int a = 2 / 0;
        return CheckResult.correct();
    }

}
