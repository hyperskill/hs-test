package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizationBoxingBooleanBad extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "boolean[] data provider isn't supported, use java.lang.Boolean[]";

    boolean[] values = {
        true, false, false, true, true
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(boolean value) {
        System.out.println(value);
        return new CheckResult(values[counter++] == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
