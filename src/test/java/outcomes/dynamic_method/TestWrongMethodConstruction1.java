package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class TestWrongMethodConstruction1 extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Unexpected error during testing");

        exception.expectMessage("UnexpectedError: " +
            "Method " +
            "\"test\" should return CheckResult object. Found: void");
    }

    @DynamicTestingMethod
    void test() {

    }
}
