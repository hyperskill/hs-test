package outcomes.dynamic_method;

import org.hyperskill.hstest.v7.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicMethodStartInBackgroundCorrectServer {
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

class TestDynamicMethodStartInBackgroundCorrectClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodStartInBackgroundCorrect extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodStartInBackgroundCorrectServer.class);

        TestedProgram client = new TestedProgram(
            TestDynamicMethodStartInBackgroundCorrectClient.class);

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

        return CheckResult.correct();
    }
}
