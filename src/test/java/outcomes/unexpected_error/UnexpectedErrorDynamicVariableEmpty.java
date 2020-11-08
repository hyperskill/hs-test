package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorDynamicVariableEmpty extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Expected non-empty array, found empty";

    @DynamicTestingMethod
    DynamicTesting[] value = { };
}
