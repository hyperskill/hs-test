package outcomes.threads;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.common.Utils.sleep;

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

public class TestThreadInterrupt2 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "interrupted inside";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(TestThreadInterrupt2Main.class);
        pr.startInBackground();
        sleep(10);
        pr.stop();
        return CheckResult.wrong("");
    }
}
