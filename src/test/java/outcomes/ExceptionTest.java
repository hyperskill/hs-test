package outcomes;

import mock.WithException;
import org.hyperskill.hstest.dev.stage.MainMethodTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class ExceptionTest extends MainMethodTest {

    public ExceptionTest() throws Exception {
        super(WithException.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1");
    }

    @Override
    public List<TestCase> generateTestCases() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }
}
