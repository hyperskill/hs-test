import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestDynamicExceptionServer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
        int x = 0/0;
        System.out.println("S2: " + scanner.nextLine());
    }
}

class TestDynamicExceptionClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client started!");
        System.out.println("C1: " + scanner.nextLine());
        System.out.println("C2: " + scanner.nextLine());
    }
}

public class TestDynamicException extends StageTest<String> {

    public TestDynamicException() {
        super(TestDynamicExceptionServer.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Exception in test #1\n" +
                "\n" +
                "java.lang.ArithmeticException: / by zero"
        );
        exception.expectMessage(
            "Please find below the output of your program during this failed test.\n" +
                "Note that the '>' character indicates the beginning of the input line.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "Server started!\n" +
                "Client started!\n" +
                "> Client started!\n" +
                "S1: Client started!"
        );

        exception.expectMessage(not(containsString("Fatal error")));
        exception.expectMessage(not(containsString("at org.hyperskill.hstest")));
        exception.expectMessage(not(containsString("org.junit.")));
        exception.expectMessage(not(containsString("at sun.reflect.")));
        exception.expectMessage(not(containsString("at java.base/jdk.internal.reflect.")));
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicInput(() -> {
                TestedProgram server = new TestedProgram(TestDynamicExceptionServer.class);
                TestedProgram client = new TestedProgram(TestDynamicExceptionClient.class);

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
            })
        );
    }
}
