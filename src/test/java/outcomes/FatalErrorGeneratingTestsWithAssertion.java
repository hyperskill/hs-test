package outcomes;

import mock.WithoutException;
import org.hyperskill.hstest.dev.stage.MainMethodTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class FatalErrorGeneratingTestsWithAssertion extends MainMethodTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public FatalErrorGeneratingTestsWithAssertion() throws Exception {
        super(WithoutException.class);
    }

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during testing, please send the report to Hyperskill team.");
    }

    @Override
    public List<TestCase> generateTestCases() {
        assert false;
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }
}
