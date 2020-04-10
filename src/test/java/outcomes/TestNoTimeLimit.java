package outcomes;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;

import java.util.Arrays;
import java.util.List;

class TestNoTimeLimitMain {
    public static void main(String[] args) throws Exception {
        Thread.sleep(16000);
    }
}

public class TestNoTimeLimit extends StageTest {

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(Boolean.getBoolean("skipSlow"));
    }

    public TestNoTimeLimit() {
        super(TestNoTimeLimitMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setTimeLimit(-1)
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
