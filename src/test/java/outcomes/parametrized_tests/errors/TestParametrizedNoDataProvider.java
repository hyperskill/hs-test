package outcomes.parametrized_tests.errors;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestParametrizedNoDataProvider extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Class TestParametrizedNoDataProvider doesn't contain " +
        "field or method named \"data\" as a data provider.";


    @DynamicTest(data = "data")
    CheckResult test1() {
        return CheckResult.correct();
    }

}
