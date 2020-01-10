package outcomes;

import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class FatalErrorRuntimeExitInGenerate extends BaseStageTest {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public FatalErrorRuntimeExitInGenerate() {
        super(FatalErrorRuntimeExitInGenerate.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error during testing, please send the report to support@hyperskill.org");
        exception.expectMessage("CheckExitCalled: Tried to exit");
    }

    @Override
    public List<TestCase> generate() {
        Runtime.getRuntime().exit(0);
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.TRUE;
    }
}
