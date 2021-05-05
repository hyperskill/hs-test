package outcomes.lib;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class TestRunTestInsideTest extends UnexpectedErrorTest {

    @ContainsMessage
    String msg = "Error during testing";

    @ContainsMessage
    public String msg2 = "Cannot start the testing process more than once";

    @DynamicTest
    CheckResult test() {
        new TestRunTestInsideTest().start();
        return CheckResult.correct();
    }
}
