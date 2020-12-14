package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedDataProviderReturnNull3 extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "data provider \"data\" in class TestParametrizedDataProviderReturnNull3 " +
        "should not be/should not return an array that contains null inside.";

    Object[] data = {
        1, 2, 3, null
    };

    @DynamicTest(data = "data")
    CheckResult test1(int x) {
        return CheckResult.correct();
    }

}
