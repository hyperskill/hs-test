package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestDynamicMethodStartInBackgroundWrongAnswerServer {
    public static void main(String[] args) {
        System.out.println("Server started!");
        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
            System.out.println("Server interrupted!");
        }
    }
}

class TestDynamicMethodStartInBackgroundWrongAnswerClient {
    public static void main(String[] args) throws Exception {
        Thread.sleep(10);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodStartInBackgroundWrongAnswer extends UserErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #1",

        "java.lang.InterruptedException: sleep interrupted\n" +
        "Server interrupted!",

        "Client started!\n" +
        "> 123\n" +
        "C1: 123\n" +
        "> 345\n" +
        "C2: 345"
    };

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodStartInBackgroundWrongAnswerServer.class);

        TestedProgram client = new TestedProgram(
            TestDynamicMethodStartInBackgroundWrongAnswerClient.class);

        server.startInBackground();
        String out = client.start();

        if (!out.equals("Client started!\n")) {
            return CheckResult.wrong("");
        }

        out = client.execute("123");
        if (!out.equals("C1: 123\n")) {
            return CheckResult.wrong("");
        }

        out = client.execute("345");
        if (!out.equals("C2: 345\n")) {
            return CheckResult.wrong("");
        }

        server.stop();

        return CheckResult.wrong("");
    }
}
