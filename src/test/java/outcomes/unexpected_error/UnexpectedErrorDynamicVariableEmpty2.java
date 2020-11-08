package outcomes.unexpected_error;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

public class UnexpectedErrorDynamicVariableEmpty2 extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Expected non-empty list, found empty";

    @DynamicTestingMethod
    List<DynamicTesting> value = Arrays.asList();
}
