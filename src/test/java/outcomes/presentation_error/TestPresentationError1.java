package outcomes.presentation_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;

class TestPresentationError1Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class TestPresentationError1 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Presentation error in test #1\n" +
        "\n" +
        "The following output contains wrong number of integers (expected to be equal to 5, found 0):\n" +
        "Hello World";

    public TestPresentationError1() {
        super(TestPresentationError1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        expect(reply).toContain(5).integers();
        return CheckResult.correct();
    }
}
