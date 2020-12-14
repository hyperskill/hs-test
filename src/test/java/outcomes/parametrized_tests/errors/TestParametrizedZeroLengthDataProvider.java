package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedZeroLengthDataProvider extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "data provider \"data\" in class TestParametrizedZeroLengthDataProvider " +
        "should be/should return an array with length > 0 of its inner arrays. Found array with length = 0";

    Object data = new Object[][] { {}, {} };

    @DynamicTest(data = "data")
    CheckResult test1() {
        return CheckResult.correct();
    }

}
