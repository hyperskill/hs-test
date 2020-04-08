package outcomes.dynamic_method;

import org.hyperskill.hstest.v7.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodStartInBackgroundWrongAnswer extends StageTest<String> {
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

        return CheckResult.wrong("");
    }
}
