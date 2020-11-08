package outcomes.presentation_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;

class TestPresentationError1InDynamicMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello world");
        scanner.next();
    }
}

public class TestPresentationError1InDynamicMethod extends UserErrorTest {

    @ContainsMessage
    String m1 = "Presentation error in test #";

    @ContainsMessage
    String m2 =
        "\n" +
        "\n" +
        "The following output contains wrong number of words (expected to be equal to 3, found 2):\n" +
        "Hello world";

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
