package outcomes;

import mock.WithoutException;
import org.hyperskill.hstest.v4.stage.MainMethodTest;
import org.hyperskill.hstest.v4.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class FatalErrorNotGeneratingTests extends MainMethodTest {

    public FatalErrorNotGeneratingTests() throws Exception {
        super(WithoutException.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during testing, please send the report to Hyperskill team.");
        exception.expectMessage("No tests found");
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }

}
