package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class UnexpectedErrorDynamicVariableWrongType extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Cannot cast the field \"value\" to List or array " +
                "or interface org.hyperskill.hstest.dynamic.input.DynamicTesting"
        );

        exception.expectMessage("Unexpected error");
    }

    @DynamicTestingMethod
    String value = "123";
}
