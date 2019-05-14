package outcomes;

import mock.WithoutException;
import org.hyperskill.hstest.dev.stage.MainMethodTest;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class FatalErrorDuringSolving extends MainMethodTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public FatalErrorDuringSolving() throws Exception {
        super(WithoutException.class);
    }

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during solving, please send the report to Hyperskill team.");
    }

    @Override
    public List<TestCase> generateTestCases() {
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public String solve(String input) {
        assert false;

        return null;
    }
}
