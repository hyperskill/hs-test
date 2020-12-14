package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizationBoxingFloatBad extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "float[] data provider isn't supported, use java.lang.Float[]";

    float[] values = {
        1.1f, 2.2f, 3.3f, 4.4f, 5.5f
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
