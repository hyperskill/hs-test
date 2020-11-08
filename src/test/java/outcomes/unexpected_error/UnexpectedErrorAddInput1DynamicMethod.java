package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Scanner;

class UnexpectedErrorAddInput1DynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }
}

public class UnexpectedErrorAddInput1DynamicMethod extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "java.lang.ArithmeticException: / by zero"
    };

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            UnexpectedErrorAddInput1DynamicMethodMain.class);
        main.start();
        int x = 0 / 0;
        main.execute("Hello");
        return CheckResult.correct();
    }
}
