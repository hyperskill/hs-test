package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizationBoxingDoubleBad extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Arguments mismatch: method \"test\" should take object of type java.lang.Double found float.";

    double[] values = {
        1.1, 2.2, 3.3, 4.4, 5.5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(float value) {
        System.out.println(value);
        return new CheckResult(values[counter++] == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
