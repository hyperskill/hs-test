import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestDynamicStartInBackgroundCorrectServer {
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

class TestDynamicStartInBackgroundCorrectClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicStartInBackgroundCorrect extends StageTest<String> {

    public TestDynamicStartInBackgroundCorrect() {
        super(TestDynamicStartInBackgroundCorrectServer.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram server = new TestedProgram(
                    TestDynamicStartInBackgroundCorrectServer.class);

                TestedProgram client = new TestedProgram(
                    TestDynamicStartInBackgroundCorrectClient.class);

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
            })
        );
    }
}
