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
            return CheckResult.wrong("");
        }

        sleep(75);

        out = main.getOutput();
        if (!out.equals("Test\n")) {
            return CheckResult.wrong("");
        }

        sleep(100);

        out = main.getOutput();
        if (!out.equals("Test\nTest\n")) {
            return CheckResult.wrong("");
        }

        main.stop();
        if (!main.isFinished()) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
