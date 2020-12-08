package outcomes.timeout;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestDefaultTimeLimit2Main {
    public static void main(String[] args) throws Exception {
        Thread.sleep(16000);
    }
}

public class TestDefaultTimeLimit2 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Error in test #1",

        "In this test, " +
        "the program is running for a long time, more than 15 seconds. " +
        "Most likely, the program has gone into an infinite loop."
    };

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(skipSlow);
    }

    @DynamicTest
    CheckResult test() {
        new TestedProgram(TestDefaultTimeLimit2Main.class).start();
        return CheckResult.correct();
    }
}
