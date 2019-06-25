package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class FatalErrorNotGeneratingTests extends BaseStageTest {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public FatalErrorNotGeneratingTests() {
        super(FatalErrorNotGeneratingTests.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during testing, please send the report to Hyperskill team.");
        exception.expectMessage("Can't create tests: override \"generate\" method");
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }

}
