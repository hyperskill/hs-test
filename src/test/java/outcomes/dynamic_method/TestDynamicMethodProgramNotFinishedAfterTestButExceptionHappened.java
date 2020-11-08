package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.NoSuchElementException;
import java.util.Scanner;

class TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappenedServer {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("S1: " + scanner.nextLine());
            Thread.sleep(100);
            System.out.println("S2: " + scanner.nextLine());
        } catch (NoSuchElementException | InterruptedException th) {
            System.out.println("Server stopped!");
            System.out.println(th.toString());
        }
    }
}

class TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappenedClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappened extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Exception in test #1\n" +
        "\n" +
        "Probably your program run out of input (Scanner tried to read more than expected).\n" +
        "\n" +
        "java.util.NoSuchElementException: No line found";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappenedServer.class);

        TestedProgram client = new TestedProgram(
            TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappenedClient.class);

        String out1 = server.start();
        String out2 = client.start();
        if (!out1.equals("Server started!\n")
            || !out2.equals("Client started!\n")) {
            return CheckResult.wrong("");
        }

        return null;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        if (reply.contains("Server stopped!\n" +
            "java.util.NoSuchElementException: No line found")
            && reply.contains("Client stopped!")) {
            return CheckResult.correct();
        }
        return CheckResult.wrong("");
    }
}
