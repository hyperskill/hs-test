package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedExceptionInDataProvider extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Exception class java.lang.Error running data provider \"data\" " +
        "in class TestParametrizedExceptionInDataProvider.";

    @ContainsMessage
    String m2 =
        "Caused by: java.lang.Error";

    Object data() {
        throw new Error();
    }

    @DynamicTest(data = "data")
    CheckResult test1() {
        return CheckResult.correct();
    }

}
