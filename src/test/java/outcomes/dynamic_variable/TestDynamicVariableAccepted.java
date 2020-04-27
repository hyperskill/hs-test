package outcomes.dynamic_variable;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class TestDynamicVariableAcceptedServer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
        System.out.println("S2: " + scanner.nextLine());
    }
}

class TestDynamicVariableAcceptedClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicVariableAccepted extends StageTest<String> {
    @DynamicTestingMethod
    List<DynamicTesting> tests = Collections.singletonList(
        () -> {
            TestedProgram server = new TestedProgram(TestDynamicVariableAcceptedServer.class);
            TestedProgram client = new TestedProgram(TestDynamicVariableAcceptedClient.class);

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
    );
}
