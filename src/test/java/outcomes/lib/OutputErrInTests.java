package outcomes.lib;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class OutputErrInTestsMain {
    public static void main(String[] args) {
        System.out.println("123");
        new Scanner(System.in).nextLine();
        System.out.println("456");
    }
}

public class OutputErrInTests extends StageTest {

    @DynamicTest
    CheckResult test() {
        var pr = new TestedProgram(OutputInTestsMain.class);
        var out = pr.start();

        if (!out.equals("123\n")) {
            return CheckResult.wrong("");
        }

        System.err.println("Test output");
        out = pr.execute("789");

        if (!out.equals("456\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }

}
