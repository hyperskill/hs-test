package outcomes;

import org.hyperskill.hstest.v7.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

class FatalErrorSystemExitInDynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class FatalErrorSystemExitInDynamicMethod extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #");
        exception.expectMessage("CheckExitCalled: Tried to exit");
    }

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram main = new TestedProgram(
            FatalErrorSystemExitInDynamicTestingMain.class);
        main.start();
        main.execute("123");
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram main = new TestedProgram(
            FatalErrorSystemExitInDynamicTestingMain.class);
        main.start();
        System.exit(0);
        main.execute("123");
        return null;
    }
}
