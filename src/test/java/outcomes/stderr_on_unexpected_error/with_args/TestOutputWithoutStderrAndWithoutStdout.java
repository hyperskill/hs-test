package outcomes.stderr_on_unexpected_error.with_args;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

class TestOutputWithoutStderrAndWithoutStdoutMain {
    public static void main(String[] args) {

    }
}

public class TestOutputWithoutStderrAndWithoutStdout extends UnexpectedErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "java.lang.ArithmeticException: / by zero"
    };

    String[] wrongLines = {
        "stderr",
        "stdout"
    };

    @DynamicTest
    CheckResult test() {
        TestedProgram testedProgram = new TestedProgram(TestOutputWithoutStderrAndWithoutStdoutMain.class);
        testedProgram.start("test", "args");
        int a = 2 / 0;
        return CheckResult.correct();
    }

}
