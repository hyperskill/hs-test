package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedWrongArgs3 extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Arguments mismatch: method \"test1\" should take object of type " +
        "java.lang.Integer found java.lang.String.";

    Object[][] data = {
        {1, 2},
        {2, 3}
    };

    @DynamicTest(data = "data")
    CheckResult test1(int x, String y) {
        return CheckResult.correct();
    }

}
