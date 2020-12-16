package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestDynamicMethodEarlyExitMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
    }
}

public class TestDynamicMethodEarlyExit extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "The program TestDynamicMethodEarlyExitMain has unexpectedly terminated.\n" +
        "It finished execution too early, should continue running.\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "Note that the '>' character indicates the beginning of the input line.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Server started!\n" +
        "> main\n" +
        "S1: main";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestDynamicMethodEarlyExitMain.class);

        String out1 = main.start();
        if (!out1.equals("Server started!\n")) {
            return CheckResult.wrong("");
        }

        out1 = main.execute("main");
        if (!out1.equals("S1: main\n")) {
            return CheckResult.wrong("");
        }

        main.execute("main2");
        return CheckResult.correct();
    }
}
