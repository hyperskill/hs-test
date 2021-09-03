package outcomes.runtime_exit;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Scanner;

import static org.hyperskill.hstest.common.JavaUtils.isSystemExitAllowed;

class UnexpectedErrorRuntimeExitInDynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class UnexpectedErrorRuntimeExitInDynamicMethod extends UnexpectedErrorTest {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(isSystemExitAllowed());
    }

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #",
        "ExitException: Tried to exit"
    };

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram(
            UnexpectedErrorRuntimeExitInDynamicMethodMain.class);
        main.start();
        main.execute("123");
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram main = new TestedProgram(
            UnexpectedErrorRuntimeExitInDynamicMethodMain.class);
        main.start();
        Runtime.getRuntime().exit(0);
        main.execute("123");
        return CheckResult.correct();
    }
}
