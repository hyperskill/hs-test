package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorDynamicMethodBadReturnType extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Method \"test\" should return CheckResult object. Found: class java.lang.String";

    @DynamicTestingMethod
    String test() {
        return "123";
    }
}
