package outcomes.repeating;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestRepeatingWrongAmount extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "DynamicTest \"test\" should not be repeated < 0 times, found -1";

    @DynamicTest(repeat = -1)
    CheckResult test(int x) {
        return CheckResult.correct();
    }
}
