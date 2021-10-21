package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestGettingOutputWhileProgramInBackgroundMain {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(50);
            System.out.println("Test");
        }
    }
}

public class TestGettingOutputWhileProgramInBackground extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestGettingOutputWhileProgramInBackgroundMain.class);

        main.startInBackground();

        String out = main.getOutput();
        if (!out.equals("")) {
            return CheckResult.wrong("1) Real out (length " + out.length() + "):\n" + out);
        }

        sleep(75);

        out = main.getOutput();
        if (!out.equals("Test\n")) {
            return CheckResult.wrong("2) Real out (length " + out.length() + "):\n" + out);
        }

        sleep(100);

        out = main.getOutput();
        if (!out.equals("Test\nTest\n")) {
            return CheckResult.wrong("3) Real out (length " + out.length() + "):\n" + out);
        }

        main.stop();
        if (!main.isFinished()) {
            return CheckResult.wrong("Main is not finished");
        }

        return CheckResult.correct();
    }
}
