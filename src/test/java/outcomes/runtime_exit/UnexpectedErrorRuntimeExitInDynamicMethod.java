package outcomes.runtime_exit;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

class UnexpectedErrorRuntimeExitInDynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class UnexpectedErrorRuntimeExitInDynamicMethod extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Unexpected error in test #");
        exception.expectMessage("ProgramExited: Tried to exit");
    }

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
