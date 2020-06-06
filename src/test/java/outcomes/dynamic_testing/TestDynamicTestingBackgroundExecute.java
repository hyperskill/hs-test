package outcomes.dynamic_testing;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestDynamicTestingBackgroundExecuteMain {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        Thread.sleep(30);
        System.out.print(line);

        scanner.nextLine();
    }
}

public class TestDynamicTestingBackgroundExecute extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundExecuteMain.class);

        pr.start();
        pr.goBackground();

        if (pr.getOutput().length() != 0) {
            return CheckResult.wrong("Should be empty before sending input");
        }

        pr.execute("123");
        sleep(20);

        if (pr.getOutput().length() != 0) {
            return CheckResult.wrong("Should be empty right after sending input");
        }

        sleep(20);

        if (!pr.getOutput().equals("123")) {
            return CheckResult.wrong("Should be not empty after waiting long enough");
        }

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundExecuteMain.class);
        pr.setReturnOutputAfterExecution(false);

        pr.start();

        if (pr.getOutput().length() != 0) {
            return CheckResult.wrong("Should be empty before sending input");
        }

        pr.execute("123");

        if (!pr.getOutput().equals("123")) {
            return CheckResult.wrong("Should be not empty because not in the background");
        }

        return CheckResult.correct();
    }
}
