package outcomes.threads;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

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

public class TestThreadInterrupt1 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "interrupted\n" +
        "exited";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(TestThreadInterrupt1Main.class);
        pr.startInBackground();
        sleep(10);
        pr.stop();
        return CheckResult.wrong("");
    }
}
