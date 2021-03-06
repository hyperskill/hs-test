package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizationBoxingIntBad extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Arguments mismatch: method \"test\" should take object of type java.lang.Integer found long.";

    Object[] values = {
        1, 2, 3, 4, 5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(long value) {
        System.out.println(value);
        return new CheckResult(++counter == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
