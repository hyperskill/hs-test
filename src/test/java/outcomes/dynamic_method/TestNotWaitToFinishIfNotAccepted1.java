package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestNotWaitToFinishIfNotAccepted1Main {
    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
        }
    }
}

public class TestNotWaitToFinishIfNotAccepted1 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Should show this feedback";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestNotWaitToFinishIfNotAccepted1Main.class);

        pr.startInBackground();
        sleep(50);

        return CheckResult.wrong("Should show this feedback");
    }
}
