package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class TestSystemExitMain {
    public static void main(String[] args) {
        System.exit(0);
    }
}

public class TestSystemExit extends UserErrorTest {

    @ContainsMessage
    String m = "Wrong answer in test #1";

    public TestSystemExit() {
        super(TestSystemExitMain.class);
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

