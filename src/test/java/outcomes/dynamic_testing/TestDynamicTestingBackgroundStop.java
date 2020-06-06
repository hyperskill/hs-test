package outcomes.dynamic_testing;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestDynamicTestingBackgroundStopMain {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        Thread.sleep(20);
        System.out.print(line);

        line = scanner.nextLine();
        Thread.sleep(30);
        System.out.print(line);

        scanner.nextLine();
    }
}

public class TestDynamicTestingBackgroundStop extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundStopMain.class);
        pr.setReturnOutputAfterExecution(false);

        pr.startInBackground();

        while (!pr.isWaitingInput()) {
            sleep(5);
        }

        pr.execute("1234");
        sleep(30);

        if (!pr.isWaitingInput() || !pr.getOutput().equals("1234")) {
            return CheckResult.wrong("Should already wait for output");
        }

        pr.stopBackground();

        pr.execute("2345");
        if (!pr.isWaitingInput() || !pr.getOutput().equals("2345")) {
            return CheckResult.wrong("Should wait for output because not in background");
        }

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundStopMain.class);
        pr.setReturnOutputAfterExecution(false);

        pr.start();
        pr.goBackground();

        pr.execute("1234");
        pr.stopBackground();

        if (!pr.isWaitingInput()) {
            return CheckResult.wrong("Should already wait for output");
        }

        pr.execute("2345");
        if (!pr.isWaitingInput()) {
            return CheckResult.wrong("Should wait for output because not in background");
        }

        return CheckResult.correct();
    }
}
