package outcomes.repeating;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestRepeatingWithParametrizedTests extends UserErrorTest {

    @ContainsMessage
    String m = "Wrong answer in test #31";

    Object[] data = {
        1, 2, 3, 4, 5, 6
    };

    @DynamicTest(repeat = 5, data = "data")
    CheckResult test(int x) {
        return CheckResult.correct();
    }

    @DynamicTest
    CheckResult test2() {
        return CheckResult.wrong("");
    }
}
