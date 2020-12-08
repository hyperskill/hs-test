package outcomes.timeout;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestNoTimeLimit2Main {
    public static void main(String[] args) throws Exception {
        Thread.sleep(16000);
    }
}

public class TestNoTimeLimit2 extends StageTest {

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(skipSlow);
    }

    @DynamicTest(timeLimit = -1)
    CheckResult test() {
        new TestedProgram(TestNoTimeLimit2Main.class).start();
        return CheckResult.correct();
    }
}
