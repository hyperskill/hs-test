package outcomes;

import org.hyperskill.hstest.v3.stage.MainMethodTest;
import org.hyperskill.hstest.v3.testcase.CheckResult;
import org.hyperskill.hstest.v3.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.Arrays;
import java.util.List;

public class SystemExitTest extends MainMethodTest {

    public static void main(String[] args) {
        System.exit(0);
    }

    public SystemExitTest() throws Exception {
        super(SystemExitTest.class);
    }

    public final ExpectedException exception = ExpectedException.none();

    @Rule
    public TestRule allRules = RuleChain.outerRule(exception).around(exit);

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Error in test #1 - Tried to exit");
    }

    @Override
    public List<TestCase> generateTestCases() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.FALSE;
    }
}

