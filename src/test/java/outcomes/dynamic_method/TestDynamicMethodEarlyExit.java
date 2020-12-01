package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestDynamicMethodEarlyExitServer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
    }
}

class TestDynamicMethodEarlyExitClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
    }
}

public class TestDynamicMethodEarlyExit extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "The program TestDynamicMethodEarlyExitServer has unexpectedly terminated.\n" +
        "It finished execution too early, should continue running.\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "Note that the '>' character indicates the beginning of the input line.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Server started!\n" +
        "Client started!\n" +
        "> Client started!\n" +
        "S1: Client started!\n" +
        "> Server started!\n" +
        "C1: Server started!";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodEarlyExitServer.class);

        TestedProgram client = new TestedProgram(
            TestDynamicMethodEarlyExitClient.class);

        String out1 = server.start();
        String out2 = client.start();
        if (!out1.equals("Server started!\n")
            || !out2.equals("Client started!\n")) {
            return CheckResult.wrong("");
        }

        String out3 = server.execute(out2);
        String out4 = client.execute(out1);
        if (!out3.equals("S1: Client started!\n")
            || !out4.equals("C1: Server started!\n")) {
            return CheckResult.wrong("");
        }

        String out5 = server.execute(out4);
        String out6 = client.execute(out3);
        if (!out5.equals("S2: C1: Server started!\n")
            || !out6.equals("C2: S1: Client started!\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
