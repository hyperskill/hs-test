package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestParametrizationBoxingDoubleGood extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #6\n" +
        "\n" +
        "counter = 5";

    double[] values = {
        1.1, 2.2, 3.3, 4.4, 5.5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(double value) {
        System.out.println(value);
        return new CheckResult(values[counter++] == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
