package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestParametrizationBoxingByteObject extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #6\n" +
        "\n" +
        "counter = 5";

    Object[] values = {
        (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(byte value) {
        System.out.println(value);
        return new CheckResult((byte) values[counter++] == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
