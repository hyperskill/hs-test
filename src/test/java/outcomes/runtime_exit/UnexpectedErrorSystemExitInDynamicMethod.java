package outcomes.runtime_exit;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Scanner;

class UnexpectedErrorSystemExitInDynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class UnexpectedErrorSystemExitInDynamicMethod extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #",
        "ProgramExited: Tried to exit"
    };

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram(
            UnexpectedErrorSystemExitInDynamicTestingMain.class);
        main.start();
        main.execute("123");
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram main = new TestedProgram(
            UnexpectedErrorSystemExitInDynamicTestingMain.class);
        main.start();
        System.exit(0);
        main.execute("123");
        return null;
    }
}
