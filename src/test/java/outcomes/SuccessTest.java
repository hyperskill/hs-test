package outcomes;

import mock.WithoutException;
import org.hyperskill.hstest.v3.stage.MainMethodTest;
import org.hyperskill.hstest.v3.testcase.CheckResult;
import org.hyperskill.hstest.v3.testcase.TestCase;

import java.util.List;

public class SuccessTest extends MainMethodTest {

    public SuccessTest() throws Exception {
        super(WithoutException.class);
    }

    @Override
    public List<TestCase> generateTestCases() {
        return List.of(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }
}
