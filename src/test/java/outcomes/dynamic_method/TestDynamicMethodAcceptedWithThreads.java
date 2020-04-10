package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicMethodAcceptedWithThreadsServer {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("Server1: " + scanner.nextLine());
            System.out.println("Server2: " + scanner.nextLine());
        });
        t.start();
        t.join();
    }
}

class TestDynamicMethodAcceptedWithThreadsClient {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Client started!");
            System.out.println("Client1: " + scanner.nextLine());
            System.out.println("Client2: " + scanner.nextLine());
        });
        t.start();
        t.join();
    }
}

public class TestDynamicMethodAcceptedWithThreads extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodAcceptedWithThreadsServer.class);

        TestedProgram client = new TestedProgram(
            TestDynamicMethodAcceptedWithThreadsClient.class);

        String out1 = server.start();
        String out2 = client.start();
        if (!out1.equals("Server started!\n")
            || !out2.equals("Client started!\n")) {
            return CheckResult.wrong("");
        }

        String out3 = server.execute(out2);
        String out4 = client.execute(out1);
        if (!out3.equals("Server1: Client started!\n")
            || !out4.equals("Client1: Server started!\n")) {
            return CheckResult.wrong("");
        }

        String out5 = server.execute(out4);
        String out6 = client.execute(out3);
        if (!out5.equals("Server2: Client1: Server started!\n")
            || !out6.equals("Client2: Server1: Client started!\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
