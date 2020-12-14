package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedWrongArgs2 extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Arguments count mismatch: method \"test1\" should take 2 parameters, found 1.";

    Object[][] data = {
        {1, 2},
        {2, 3}
    };

    @DynamicTest(data = "data")
    CheckResult test1(int x) {
        return CheckResult.correct();
    }

}
