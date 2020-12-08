package outcomes.timeout;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestTimeout7Main {
    public static void main(String[] args) {
        while (true) { }
    }
}

public class TestTimeout7 extends UserErrorTest {

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

    @DynamicTest(timeLimit = 2000)
    CheckResult test1() {
        new TestedProgram(TestTimeout7Main.class).start();
        return CheckResult.correct();
    }
}
