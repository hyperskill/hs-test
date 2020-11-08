package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorDynamicVariableContainsNull extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Expected array without nulls";

    @DynamicTestingMethod
    DynamicTesting[] value = {
        CheckResult::correct,
        null
    };
}
