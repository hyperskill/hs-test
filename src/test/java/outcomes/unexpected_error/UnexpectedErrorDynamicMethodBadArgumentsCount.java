package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorDynamicMethodBadArgumentsCount extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Method \"test\" should take 0 arguments. Found: 2";

    @DynamicTestingMethod
    CheckResult test(String x, String y) {
        return CheckResult.correct();
    }
}
