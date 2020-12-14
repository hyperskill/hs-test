package outcomes.parametrized_tests.boxing;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizationBoxingCharBad extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "char[] data provider isn't supported, use java.lang.Character[]";

    char[] values = {
        1, 2, 3, 4, 5
    };

    int counter = 0;

    @DynamicTest(data = "values")
    CheckResult test(char value) {
        System.out.println(value);
        return new CheckResult(values[counter++] == value, "");
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("counter = " + counter);
    }

}
