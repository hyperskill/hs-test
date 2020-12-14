package outcomes.repeating;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestRepeating2 extends UserErrorTest {

    @ContainsMessage
    String m = "Wrong answer in test #6";

    @DynamicTest(repeat = 5)
    CheckResult test() {
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("");
    }

    @DynamicTest(repeat = 5)
    CheckResult test3() {
        return CheckResult.correct();
    }

}
