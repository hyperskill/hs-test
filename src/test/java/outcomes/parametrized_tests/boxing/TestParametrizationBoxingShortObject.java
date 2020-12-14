package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestParametrizationBoxingShortObject extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #6\n" +
        "\n" +
        "counter = 5";

    Object[] values = {
        (short) 1, (short) 2, (short) 3, (short) 4, (short) 5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(short value) {
        System.out.println(value);
        return new CheckResult((short) values[counter++] == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
