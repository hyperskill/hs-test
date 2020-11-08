package outcomes.presentation_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;

class TestPresentationError1InDynamicTestingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello world");
        scanner.next();
    }
}

public class TestPresentationError1InDynamicTesting extends UserErrorTest {

    @ContainsMessage
    String m =
        "Presentation error in test #2\n" +
        "\n" +
        "The following output contains wrong number of words (expected to be equal to 3, found 2):\n" +
        "Hello world";

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestPresentationError1InDynamicTestingMain.class);
                String out = pr.start();
                expect(out).toContain(2).words();
                return CheckResult.correct();
            }),

            new TestCase<>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestPresentationError1InDynamicTestingMain.class);
                String out = pr.start();
                expect(out).toContain(3).words();
                return CheckResult.correct();
            })
        );
    }
}
