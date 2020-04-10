package outcomes.input_mismatch;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

class InputMismatchDynamicMethodMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(s.nextInt());
        System.out.println(s.nextLine());
        System.out.println(s.nextInt());
    }
}

public class InputMismatchDynamicMethod extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1");
        exception.expectMessage("Probably you have nextInt() " +
            "(or similar Scanner method) followed by nextLine() - " +
            "in this situation nextLine() often gives an " +
            "empty string and another one nextLine() call gives correct string.");
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(InputMismatchDynamicMethodMain.class);
        pr.start();
        pr.execute("234\nqwe\n345");
        return CheckResult.correct();
    }
}
