package outcomes.lib;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class OutputInTests2Main {
    public static void main(String[] args) {
        System.out.println("123");
        new Scanner(System.in).nextLine();
        System.out.println("456");
    }
}

public class OutputInTests2 extends UserErrorTest {

    @ContainsMessage
    String msg =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "Note that the '>' character indicates the beginning of the input line.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "123\n" +
        "> 789\n" +
        "456";

    @DynamicTest
    CheckResult test() {
        var pr = new TestedProgram(OutputInTestsMain.class);
        var out = pr.start();

        if (!out.equals("123\n")) {
            return CheckResult.wrong("");
        }

        System.out.println("Test output");
        pr.execute("789");

        return CheckResult.wrong("");
    }

}
