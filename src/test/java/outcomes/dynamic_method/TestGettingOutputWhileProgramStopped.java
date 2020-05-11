package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestGettingOutputWhileProgramStoppedMain {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            sleep(50);
            System.out.println("4 5 6");
            sleep(50);
            System.out.println("7 8 9");
        });

        System.out.println("1 2 3");
        t.start();
        new Scanner(System.in).nextLine();
        System.out.println("10 11 12");
        t.join();
    }
}

public class TestGettingOutputWhileProgramStopped extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestGettingOutputWhileProgramStoppedMain.class);

        String out = main.start();
        if (!out.equals("1 2 3\n")) {
            return CheckResult.wrong("");
        }

        sleep(75);

        out = main.getOutput();
        if (!out.equals("4 5 6\n")) {
            return CheckResult.wrong("");
        }

        sleep(50);

        out = main.getOutput();
        if (!out.equals("7 8 9\n")) {
            return CheckResult.wrong("");
        }

        out = main.execute("");
        if (!out.equals("10 11 12\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
