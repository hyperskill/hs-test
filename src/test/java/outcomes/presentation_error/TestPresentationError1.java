package outcomes.presentation_error;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hyperskill.hstest.testing.expect.Expectation.expect;

class TestPresentationError1Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class TestPresentationError1 extends StageTest {

    public TestPresentationError1() {
        super(TestPresentationError1Main.class);
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
                "Hello World"
        );

        exception.expectMessage(not(containsString("Unexpected error")));
        exception.expectMessage(not(containsString("at org.hyperskill.hstest")));
        exception.expectMessage(not(containsString("org.junit.")));
        exception.expectMessage(not(containsString("at sun.reflect.")));
        exception.expectMessage(not(containsString("at java.base/jdk.internal.reflect.")));
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
