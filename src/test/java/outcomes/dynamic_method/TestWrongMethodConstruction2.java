package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestWrongMethodConstruction2 extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error during testing",
        "UnexpectedError: Method \"test\" should take 0 arguments. Found: 1"
    };

    @DynamicTestingMethod
    CheckResult test(int x) {
        return null;
    }
}
