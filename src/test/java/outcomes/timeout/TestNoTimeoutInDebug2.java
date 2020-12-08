package outcomes.timeout;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.ExecutionOptions;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestNoTimeoutInDebug2Main {
    public static void main(String[] args) throws Exception {
        Thread.sleep(1000);
        System.out.println("123");
    }
}

public class TestNoTimeoutInDebug2 extends StageTest {

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(skipSlow);
    }

    static boolean oldDebug;

    @Before
    public void before() {
        oldDebug = ExecutionOptions.debugMode;
        ExecutionOptions.debugMode = true;
    }

    @After
    public void after() {
        ExecutionOptions.debugMode = oldDebug;
    }

    @DynamicTest(timeLimit = 100)
    CheckResult test() {
        String reply = new TestedProgram(TestNoTimeoutInDebug2Main.class).start();
        return new CheckResult(reply.equals("123\n"), "");
    }
}
