package exceptions;

import mock.WithoutException;
import org.hyperskill.hstest.dev.stage.MainMethodTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.List;

public class WithoutExceptionTest extends MainMethodTest {

    public WithoutExceptionTest() throws Exception {
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
