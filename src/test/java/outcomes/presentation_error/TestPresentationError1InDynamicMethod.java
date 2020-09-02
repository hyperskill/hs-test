package outcomes.presentation_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;

class TestPresentationError1InDynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello world");
        scanner.next();
    }
}

public class TestPresentationError1InDynamicMethod extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Presentation error in test #"
        );
        exception.expectMessage("\n" +
            "\n" +
            "The following output contains wrong number of words (expected to be equal to 3, found 2):\n" +
            "Hello world"
        );

        exception.expectMessage(not(containsString("Unexpected error")));
        exception.expectMessage(not(containsString("at org.hyperskill.hstest")));
        exception.expectMessage(not(containsString("org.junit.")));
        exception.expectMessage(not(containsString("at sun.reflect.")));
        exception.expectMessage(not(containsString("at java.base/jdk.internal.reflect.")));
    }

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestPresentationError1InDynamicMethodMain.class);
        String out = pr.start();
        expect(out).toContain(2).words();
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestPresentationError1InDynamicMethodMain.class);
        String out = pr.start();
        expect(out).toContain(3).words();
        return CheckResult.correct();
    }
}
