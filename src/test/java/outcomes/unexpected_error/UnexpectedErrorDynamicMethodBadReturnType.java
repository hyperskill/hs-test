package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class UnexpectedErrorDynamicMethodBadReturnType extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Method \"test\" should return CheckResult object. Found: class java.lang.String"
        );

        exception.expectMessage("Unexpected error");
    }

    @DynamicTestingMethod
    String test() {
        return "123";
    }
}
