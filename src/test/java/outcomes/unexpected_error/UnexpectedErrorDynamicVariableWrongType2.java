package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorDynamicVariableWrongType2 extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Cannot cast the field \"value\" to List or array " +
        "or interface org.hyperskill.hstest.dynamic.input.DynamicTesting";

    @DynamicTestingMethod
    String[] value = { "123" };
}
