package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class UnexpectedErrorAddInput1Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }
}

public class UnexpectedErrorAddInput1 extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "java.lang.ArithmeticException: / by zero"
    };

    public UnexpectedErrorAddInput1() {
        super(UnexpectedErrorAddInput1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .addInput(out -> {
                    int x = 0 / 0;
                    return "Hello";
                })
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
