package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;


class FatalErrorGeneratingTestsWithAssertionMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class FatalErrorGeneratingTestsWithAssertion extends BaseStageTest {

    public FatalErrorGeneratingTestsWithAssertion() {
        super(FatalErrorGeneratingTestsWithAssertionMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during testing, please send the report to Hyperskill team.");
    }

    @Override
    public List<TestCase> generate() {
        assert false;
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.TRUE;
    }
}
