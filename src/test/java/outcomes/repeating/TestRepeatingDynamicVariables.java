package outcomes.repeating;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestRepeatingDynamicVariables extends UserErrorTest {

    @ContainsMessage
    String m = "Wrong answer in test #26";

    @DynamicTest(repeat = 5)
    DynamicTesting[] test = {
        CheckResult::correct,
        CheckResult::correct,
        CheckResult::correct,
        CheckResult::correct,
        CheckResult::correct
    };

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("");
    }
}
