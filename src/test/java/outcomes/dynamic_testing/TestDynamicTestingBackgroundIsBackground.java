package outcomes.dynamic_testing;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicTestingBackgroundIsBackgroundMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}

public class TestDynamicTestingBackgroundIsBackground extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundIsBackgroundMain.class);

        pr.start();

        if (pr.isInBackground()) {
            return CheckResult.wrong("");
        }

        pr.goBackground();

        if (!pr.isInBackground()) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundIsBackgroundMain.class);

        pr.startInBackground();

        if (!pr.isInBackground()) {
            return CheckResult.wrong("");
        }

        pr.stopBackground();

        if (pr.isInBackground()) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
