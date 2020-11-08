package outcomes.dynamic_testing;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestDynamicTestingStartInBackgroundWrongAnswerServer {
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

class TestDynamicTestingStartInBackgroundWrongAnswerClient {
    public static void main(String[] args) throws Exception {
        Thread.sleep(100);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicTestingStartInBackgroundWrongAnswer extends UserErrorTest<String> {

    @ContainsMessage
    String m1 = "Wrong answer in test #1";

    @ContainsMessage
    String m2 =
        "java.lang.InterruptedException: sleep interrupted\n" +
        "Server interrupted!";

    @ContainsMessage
    String m3 =
        "Client started!\n" +
        "> 123\n" +
        "C1: 123\n" +
        "> 345\n" +
        "C2: 345";

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram server = new TestedProgram(
                    TestDynamicTestingStartInBackgroundWrongAnswerServer.class);

                TestedProgram client = new TestedProgram(
                    TestDynamicTestingStartInBackgroundWrongAnswerClient.class);

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
            })
        );
    }
}
