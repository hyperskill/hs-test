package outcomes.timeout;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.ExecutionOptions;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.Collections;
import java.util.List;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestNoTimeoutInDebugMain {
    public static void main(String[] args) throws Exception {
        Thread.sleep(1000);
        System.out.println("123");
    }
}

public class TestNoTimeoutInDebug extends StageTest {

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(skipSlow);
    }

    public TestNoTimeoutInDebug() {
        super(TestNoTimeoutInDebugMain.class);
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

    @Override
    public List<TestCase> generate() {
        return Collections.singletonList(
            new TestCase<>()
                .setTimeLimit(100)
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("123\n"), "");
    }
}
