package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class UnexpectedErrorDynamicVariableWrongType3 extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Expected list of values " +
                "of interface org.hyperskill.hstest.dynamic.input.DynamicTesting, " +
                "found value of class java.lang.String"
        );

        exception.expectMessage("Unexpected error");
    }

    @DynamicTestingMethod
    List<?> value = Arrays.asList(
        (DynamicTesting) CheckResult::correct,
        "123"
    );
}
