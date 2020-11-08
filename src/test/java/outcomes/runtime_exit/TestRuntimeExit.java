package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class TestRuntimeExitMain {
    public static void main(String[] args) {
        Runtime.getRuntime().exit(0);
    }
}

public class TestRuntimeExit extends UserErrorTest {

    @ContainsMessage
    String m = "Wrong answer in test #1";

    public TestRuntimeExit() {
        super(TestRuntimeExitMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.wrong("");
    }
}
