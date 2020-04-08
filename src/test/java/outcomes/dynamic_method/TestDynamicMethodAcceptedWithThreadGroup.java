package outcomes.dynamic_method;

import org.hyperskill.hstest.v7.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicMethodAcceptedWithThreadGroupServer {
    public static void main(String[] args) throws Exception {
        ThreadGroup group = new ThreadGroup("S");
        Thread t = new Thread(group, () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("Server1: " + scanner.nextLine());
            System.out.println("Server2: " + scanner.nextLine());
        });
        t.start();
        t.join();
    }
}

class TestDynamicMethodAcceptedWithThreadGroupClient {
    public static void main(String[] args) throws Exception {
        ThreadGroup group = new ThreadGroup("C");
        Thread t = new Thread(group, () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Client started!");
            System.out.println("Client1: " + scanner.nextLine());
            System.out.println("Client2: " + scanner.nextLine());
        });
        t.start();
        t.join();
    }
}

public class TestDynamicMethodAcceptedWithThreadGroup extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodAcceptedWithThreadGroupServer.class);

        TestedProgram client = new TestedProgram(
            TestDynamicMethodAcceptedWithThreadGroupClient.class);

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
