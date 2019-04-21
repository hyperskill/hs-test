package outcomes;

import mock.WithoutException;
import org.hyperskill.hstest.dev.stage.MainMethodTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class Success extends MainMethodTest<String> {

    public Success() throws Exception {
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
