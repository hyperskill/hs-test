package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class UnexpectedErrorDynamicMethodBadArgumentsCount extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Method \"test\" should take 0 arguments. Found: 2"
        );

        exception.expectMessage("Unexpected error");
    }

    @DynamicTestingMethod
    CheckResult test(String x, String y) {
        return CheckResult.correct();
    }
}
