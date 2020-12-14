package outcomes.repeating;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestRepeatingWrongAmount extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "DynamicTest \"test\" should not be repeated <= 1 times, found 0";

    @DynamicTest(repeat = 0)
    CheckResult test(int x) {
        return CheckResult.correct();
    }
}
