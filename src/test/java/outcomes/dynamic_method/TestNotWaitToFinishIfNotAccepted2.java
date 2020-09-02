package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hyperskill.hstest.common.Utils.sleep;

class TestNotWaitToFinishIfNotAccepted2Main {
    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
        }
    }
}

public class TestNotWaitToFinishIfNotAccepted2 extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #1\n" +
                "\n" +
                "Should show this feedback"
        );
        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestNotWaitToFinishIfNotAccepted2Main.class);

        pr.startInBackground();
        sleep(50);

        throw new WrongAnswer("Should show this feedback");
    }
}
