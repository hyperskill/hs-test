package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedWrongArgs4 extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Arguments mismatch: method \"test1\" should take object of type java.lang.Long found int.";

    Object[] data = {
        1, 2, 3, 4, 5L
    };

    @DynamicTest(data = "data")
    CheckResult test1(int x) {
        return CheckResult.correct();
    }

}
