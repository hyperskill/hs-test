package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorDynamicMethodReturnsNull extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Can't check result: override \"check\" method";

    @DynamicTestingMethod
    CheckResult test() {
        return null;
    }
}
