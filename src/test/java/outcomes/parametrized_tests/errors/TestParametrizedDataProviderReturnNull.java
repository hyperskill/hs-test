package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedDataProviderReturnNull extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "data provider \"data\" in class TestParametrizedDataProviderReturnNull " +
        "should not return null, found null";

    Object data() {
        return null;
    }

    @DynamicTest(data = "data")
    CheckResult test1() {
        return CheckResult.correct();
    }

}
