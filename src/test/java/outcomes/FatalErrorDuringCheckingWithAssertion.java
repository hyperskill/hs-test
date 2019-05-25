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

public class FatalErrorDuringCheckingWithAssertion extends MainMethodTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public FatalErrorDuringCheckingWithAssertion() throws Exception {
        super(WithoutException.class);
    }

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #1, please send the report to Hyperskill team.");
    }

    @Override
    public List<TestCase> generateTestCases() {
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        assert false;
        return CheckResult.TRUE;
    }
}
