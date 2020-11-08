package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorDynamicVariableIsNull extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Expected non-null value, found null";

    @DynamicTestingMethod
    DynamicTesting value = null;
}
