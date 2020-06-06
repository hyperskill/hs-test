package outcomes.dynamic_testing;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestDynamicTestingBackgroundStillWaitingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}

public class TestDynamicTestingBackgroundStillWaiting extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundStillWaitingMain.class);

        pr.start();

        if (!pr.isWaitingInput() || pr.isFinished()) {
            return CheckResult.wrong("");
        }

        pr.goBackground();

        if (!pr.isWaitingInput() || pr.isFinished()) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundStillWaitingMain.class);

        pr.startInBackground();

        sleep(100);

        if (!pr.isWaitingInput() || pr.isFinished()) {
            return CheckResult.wrong("");
        }

        pr.goBackground();

        if (!pr.isWaitingInput() || pr.isFinished()) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
