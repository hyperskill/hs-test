package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;


class FatalErrorRuntimeExitInCheckMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class FatalErrorRuntimeExitInCheck extends BaseStageTest {

    public FatalErrorRuntimeExitInCheck() {
        super(FatalErrorRuntimeExitInCheckMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #1, please send the report to Hyperskill team.");
        exception.expectMessage("CheckExitCalled: Tried to exit");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        Runtime.getRuntime().exit(0);
        return CheckResult.TRUE;
    }
}
