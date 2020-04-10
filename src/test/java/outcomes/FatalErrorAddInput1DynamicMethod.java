package outcomes;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

class FatalErrorAddInput1DynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }
}

public class FatalErrorAddInput1DynamicMethod extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #1, please send the report to support@hyperskill.org");
        exception.expectMessage("java.lang.ArithmeticException: / by zero");
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            FatalErrorAddInput1DynamicMethodMain.class);
        main.start();
        int x = 0 / 0;
        main.execute("Hello");
        return CheckResult.correct();
    }
}
