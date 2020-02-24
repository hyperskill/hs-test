package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;


class TestNoTimeLimitMain {
    public static void main(String[] args) throws Exception {
        Thread.sleep(16000);
    }
}

public class TestNoTimeLimit extends StageTest {

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
