package outcomes.dynamic_testing;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

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

public class TestDynamicTestingStartInBackgroundWrongAnswer extends StageTest<String> {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #1"
        );
        exception.expectMessage(
            "java.lang.InterruptedException: sleep interrupted\n" +
            "Server interrupted!");
        exception.expectMessage(
            "Client started!\n" +
            "> 123\n" +
            "C1: 123\n" +
            "> 345\n" +
            "C2: 345");
        exception.expectMessage(not(containsString("Fatal error")));
    }

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
