package outcomes.timeout;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestTimeout3Main {
    public static void main(String[] args) {
        while (true) { }
    }
}

public class TestTimeout3 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Error in test #1",

        "In this test, " +
        "the program is running for a long time, more than 2 seconds. " +
        "Most likely, the program has gone into an infinite loop."
    };

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(skipSlow);
    }

    public TestTimeout3() {
        super(TestTimeout3Main.class);
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
