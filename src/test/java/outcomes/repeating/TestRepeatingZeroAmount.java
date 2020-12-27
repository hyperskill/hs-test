package outcomes.repeating;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestRepeatingZeroAmount extends UnexpectedErrorTest {
    @ContainsMessage
    String s = "UnexpectedError: No tests found";

    @DynamicTest(repeat = 0)
    CheckResult test() {
        return CheckResult.correct();
    }
}
