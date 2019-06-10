package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.PredefinedIOTestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class FatalErrorEmptyPredefinedTests extends BaseStageTest {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public FatalErrorEmptyPredefinedTests() {
        super(FatalErrorEmptyPredefinedTests.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during testing, please send the report to Hyperskill team.");
        exception.expectMessage("No tests provided by generatePredefinedInputOutput method");
    }

    @Override
    public List<PredefinedIOTestCase> generatePredefinedInputOutput() {
        return Arrays.asList();
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }
}
