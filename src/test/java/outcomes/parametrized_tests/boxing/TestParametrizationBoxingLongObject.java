package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestParametrizationBoxingLongObject extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #6\n" +
        "\n" +
        "counter = 5";

    Object[] values = {
        1L, 2L, 3L, 4L, 5L
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
