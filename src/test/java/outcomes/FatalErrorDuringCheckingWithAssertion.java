package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;


class FatalErrorDuringCheckingWithAssertionMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class FatalErrorDuringCheckingWithAssertion extends StageTest {

    public FatalErrorDuringCheckingWithAssertion() {
        super(FatalErrorDuringCheckingWithAssertionMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #1, please send the report to support@hyperskill.org");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        assert false;
        return CheckResult.TRUE;
    }
}
