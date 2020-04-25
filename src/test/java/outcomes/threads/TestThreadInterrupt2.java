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
import static org.hyperskill.hstest.v6.common.Utils.sleep;

class TestThreadInterrupt2Main {
    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("123");
        Thread t = new Thread(group, () -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("interrupted inside");
                    break;
                }
            }
        });
        t.start();
        while (t.isAlive()) {
            try {
                t.join();
            } catch (InterruptedException ignored) { }
        }
    }
}

public class TestThreadInterrupt2 extends StageTest {
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
                "interrupted inside"
        );

        exception.expectMessage(not(containsString("Fatal error")));
    }

    @DynamicTestingMethod
    CheckResult test() {
        new TestedProgram(TestThreadInterrupt2Main.class).startInBackground();
        sleep(10);
        return CheckResult.wrong("");
    }
}
