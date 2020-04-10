package outcomes.presentation_error;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;

class TestPresentationError2Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class TestPresentationError2 extends StageTest {

    public TestPresentationError2() {
        super(TestPresentationError2Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Presentation error in test #1\n" +
                "\n" +
                "The following output contains wrong number of integers (expected to be equal to 5, found 0):\n" +
                "Hell\n" +
                "\n" +
                "Please find below the output of your program during this failed test.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "Hello World"
        );
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        expect(reply.substring(0, 4)).toContain(5).integers();
        return CheckResult.correct();
    }
}
