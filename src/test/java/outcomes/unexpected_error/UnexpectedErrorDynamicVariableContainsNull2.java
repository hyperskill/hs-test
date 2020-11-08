package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

public class UnexpectedErrorDynamicVariableContainsNull2 extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Expected list without nulls";

    @DynamicTestingMethod
    List<DynamicTesting> value = Arrays.asList(
        CheckResult::correct,
        null
    );
}
