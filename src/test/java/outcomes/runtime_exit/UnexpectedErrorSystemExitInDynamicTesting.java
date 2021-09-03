package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hyperskill.hstest.common.JavaUtils.isSystemExitAllowed;

class UnexpectedErrorSystemExitInDynamicTestingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class UnexpectedErrorSystemExitInDynamicTesting extends UnexpectedErrorTest {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(isSystemExitAllowed());
    }

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #2",
        "ExitException: Tried to exit"
    };

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setDynamicTesting(() -> {
                TestedProgram main = new TestedProgram(
                    UnexpectedErrorSystemExitInDynamicTestingMain.class);
                main.start();
                main.execute("123");
                return CheckResult.correct();
            }),

            new TestCase().setDynamicTesting(() -> {
                TestedProgram main = new TestedProgram(
                    UnexpectedErrorSystemExitInDynamicTestingMain.class);
                main.start();
                System.exit(0);
                main.execute("123");
                return null;
            })
        );
    }
}
