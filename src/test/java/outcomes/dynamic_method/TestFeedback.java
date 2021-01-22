package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestFeedback extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "feedback 2\n" +
        "\n" +
        "feedback 1";

    @DynamicTest(feedback = "feedback 1")
    CheckResult test() {
        return new CheckResult(false, "feedback 2");
    }
}
