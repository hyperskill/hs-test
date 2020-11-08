package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class UnexpectedErrorRuntimeExitInDynamicTestingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class UnexpectedErrorRuntimeExitInDynamicTesting extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #2",
        "ProgramExited: Tried to exit"
    };

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setDynamicTesting(() -> {
                TestedProgram main = new TestedProgram(
                    UnexpectedErrorRuntimeExitInDynamicTestingMain.class);
                main.start();
                main.execute("123");
                return CheckResult.correct();
            }),

            new TestCase().setDynamicTesting(() -> {
                TestedProgram main = new TestedProgram(
                    UnexpectedErrorRuntimeExitInDynamicTestingMain.class);
                main.start();
                Runtime.getRuntime().exit(0);
                main.execute("123");
                return CheckResult.correct();
            })
        );
    }
}
