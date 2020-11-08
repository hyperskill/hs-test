package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class UnexpectedErrorAddInput2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.hasNextLine());
    }
}

public class UnexpectedErrorAddInput2 extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "java.lang.ArithmeticException: / by zero"
    };

    public UnexpectedErrorAddInput2() {
        super(UnexpectedErrorAddInput2Main.class);
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
