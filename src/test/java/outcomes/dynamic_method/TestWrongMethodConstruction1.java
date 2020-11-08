package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestWrongMethodConstruction1 extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error during testing",
        "UnexpectedError: Method \"test\" should return CheckResult object. Found: void"
    };

    @DynamicTestingMethod
    void test() {

    }
}
