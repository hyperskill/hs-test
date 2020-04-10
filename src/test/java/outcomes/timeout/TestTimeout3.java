package outcomes.timeout;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestTimeout3Main {
    public static void main(String[] args) {
        while (true) { }
    }
}

public class TestTimeout3 extends StageTest {

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(Boolean.getBoolean("skipSlow"));
    }

    public TestTimeout3() {
        super(TestTimeout3Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Error in test #1");
        exception.expectMessage("In this test, " +
            "the program is running for a long time, more than 2 seconds. " +
            "Most likely, the program has gone into an infinite loop.");
        exception.expectMessage(not(containsString("Fatal error")));
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setTimeLimit(2000)
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
