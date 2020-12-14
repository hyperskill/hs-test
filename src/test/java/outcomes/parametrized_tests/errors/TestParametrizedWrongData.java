package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedWrongData extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "data provider \"data\" in class TestParametrizedWrongData " +
        "should be/should return array, found java.lang.String";

    Object data = "123";

    @DynamicTest(data = "data")
    CheckResult test1(int x, String y) {
        return CheckResult.correct();
    }

}
