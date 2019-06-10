package outcomes;

import mock.WithoutException;
import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class Success extends BaseStageTest<String> {

    public Success() {
        super(WithoutException.class);
    }

    String succ = "123";

    @Override
    public List<TestCase<String>> generateTestCases() {
        return Arrays.asList(
            new TestCase<String>().setAttach(succ)
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        if (clue == null) {
            System.out.println(0 / 0);
        }
        return CheckResult.TRUE;
    }
}
