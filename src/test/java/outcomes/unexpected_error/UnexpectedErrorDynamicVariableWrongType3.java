package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

public class UnexpectedErrorDynamicVariableWrongType3 extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Expected list of values " +
        "of interface org.hyperskill.hstest.dynamic.input.DynamicTesting, " +
        "found value of class java.lang.String";

    @DynamicTestingMethod
    List<?> value = Arrays.asList(
        (DynamicTesting) CheckResult::correct,
        "123"
    );
}
