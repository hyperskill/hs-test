package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedTwoDataProviders extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Found both field and method named \"data\" " +
        "as a data provider in class TestParametrizedTwoDataProviders. Leave one of them.";

    Object data() {
        return new int[]{1, 2, 3};
    }

    Object data = new Object[] {
        1, 2, 3
    };

    @DynamicTest(data = "data")
    CheckResult test1(int x) {
        return CheckResult.correct();
    }

}
