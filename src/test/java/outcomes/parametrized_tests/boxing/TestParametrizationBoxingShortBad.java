package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizationBoxingShortBad extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "short[] data provider isn't supported, use java.lang.Short[]";

    short[] values = {
        1, 2, 3, 4, 5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(short value) {
        System.out.println(value);
        return new CheckResult(values[counter++] == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
