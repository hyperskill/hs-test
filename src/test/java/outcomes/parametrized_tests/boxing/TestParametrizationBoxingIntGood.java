package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestParametrizationBoxingIntGood extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #6\n" +
        "\n" +
        "counter = 5";

    int[] values = {
        1, 2, 3, 4, 5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(int value) {
        System.out.println(value);
        return new CheckResult(++counter == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
