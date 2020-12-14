package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedWrongData2 extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Found arrays and non-array objects inside data provider" +
        " \"data\" in class TestParametrizedWrongData2. Leave one of them.";

    Object[] data = {
        new Object[]{1, 2, 3},
        "123",
        "234",
        "345",
    };

    @DynamicTest(data = "data")
    CheckResult test1() {
        return CheckResult.correct();
    }

}
