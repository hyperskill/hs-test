package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedWrongArgs5 extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "data provider \"data\" in class TestParametrizedWrongArgs5 should " +
        "be/should return an array with equal length of its inner arrays. Found sizes: 1, 3";

    Object[] data = {
        1, 2, 3, 4, new Object[]{ 1, 2, 3 }
    };

    @DynamicTest(data = "data")
    CheckResult test1(int x) {
        return CheckResult.correct();
    }

}
