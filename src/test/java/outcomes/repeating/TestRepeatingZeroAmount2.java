package outcomes.repeating;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestRepeatingZeroAmount2 extends UserErrorTest {
    @ContainsMessage
    String s = "Wrong answer in test #3";

    @DynamicTest(repeat = 2)
    CheckResult test() {
        return CheckResult.correct();
    }

    @DynamicTest(repeat = 0)
    CheckResult test1() {
        return CheckResult.correct();
    }

    @DynamicTest(repeat = 2)
    CheckResult test2() {
        return CheckResult.wrong("");
    }
}
