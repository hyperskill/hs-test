package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class InputMismatchDynamicTestingMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(s.nextInt());
        System.out.println(s.nextLine());
        System.out.println(s.nextInt());
    }
}

public class InputMismatchDynamicTesting extends StageTest {

    public InputMismatchDynamicTesting() {
        super(InputMismatchDynamicTestingMain.class);
    }

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

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(InputMismatchDynamicTestingMain.class);
                pr.start();
                pr.execute("234\nqwe\n345");
                return CheckResult.correct();
            })
        );
    }
}
