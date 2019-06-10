package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class FatalErrorGeneratingTests extends BaseStageTest {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public FatalErrorGeneratingTests() {
        super(FatalErrorGeneratingTests.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during testing, please send the report to Hyperskill team.");
    }

    @Override
    public List<TestCase> generateTestCases() {
        System.out.println(1 / 0);
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }
}
