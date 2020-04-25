package outcomes.threads;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hyperskill.hstest.common.Utils.sleep;

class TestThreadInterrupt1Main {
    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("interrupted");
                break;
            }
        }
        System.out.println("exited");
    }
}

public class TestThreadInterrupt1 extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #1\n" +
                "\n" +
                "Please find below the output of your program during this failed test.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "interrupted\n" +
                "exited"
        );

        exception.expectMessage(not(containsString("Fatal error")));
    }

    @DynamicTestingMethod
    CheckResult test() {
        new TestedProgram(TestThreadInterrupt1Main.class).startInBackground();
        sleep(10);
        return CheckResult.wrong("");
    }
}
