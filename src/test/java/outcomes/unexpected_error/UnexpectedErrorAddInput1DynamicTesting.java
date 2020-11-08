package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class UnexpectedErrorAddInput1DynamicTestingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }
}

public class UnexpectedErrorAddInput1DynamicTesting extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "java.lang.ArithmeticException: / by zero"
    };

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setDynamicTesting(() -> {
                TestedProgram main = new TestedProgram(
                    UnexpectedErrorAddInput1DynamicTestingMain.class);
                main.start();
                int x = 0 / 0;
                main.execute("Hello");
                return CheckResult.correct();
            })
        );
    }
}
