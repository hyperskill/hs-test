package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedDataProviderReturnNull2 extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "data provider \"data\" in class TestParametrizedDataProviderReturnNull2 " +
        "should not return null, found null";

    Object data = null;

    @DynamicTest(data = "data")
    CheckResult test1() {
        return CheckResult.correct();
    }

}
