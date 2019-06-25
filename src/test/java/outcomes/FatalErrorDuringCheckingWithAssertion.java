package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class FatalErrorDuringCheckingWithAssertion extends BaseStageTest {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public FatalErrorDuringCheckingWithAssertion() {
        super(FatalErrorDuringCheckingWithAssertion.class);
    }

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #1, please send the report to Hyperskill team.");
    }

    @Override
    public List<TestCase> generate() {
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
