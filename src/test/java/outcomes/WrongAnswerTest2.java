package outcomes;

import mock.WithoutException;
import org.hyperskill.hstest.v3.stage.MainMethodTest;
import org.hyperskill.hstest.v3.testcase.CheckResult;
import org.hyperskill.hstest.v3.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.List;

public class WrongAnswerTest2 extends MainMethodTest<Boolean> {

    public WrongAnswerTest2() throws Exception {
        super(WithoutException.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #2");
    }

    @Override
    public List<TestCase<Boolean>> generateTestCases() {
        return List.of(
            new TestCase<Boolean>().setAttach(true),
            new TestCase<Boolean>().setAttach(false)
        );
    }

    @Override
    public CheckResult check(String reply, Boolean clue) {
        return new CheckResult(clue);
    }
}
