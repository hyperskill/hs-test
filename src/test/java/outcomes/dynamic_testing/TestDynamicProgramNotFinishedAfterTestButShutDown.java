package outcomes.dynamic_testing;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestDynamicProgramNotFinishedAfterTestButShutDownServer {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("S1: " + scanner.nextLine());
            Thread.sleep(1000);
            System.out.println("S2: " + scanner.nextLine());
        } catch (Throwable th) {
            System.out.println("Server stopped!");
            System.out.println(th.toString());
        }
    }
}

class TestDynamicProgramNotFinishedAfterTestButShutDownClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Client started!");
            System.out.println("C1: " + scanner.nextLine());
            System.out.println("C2: " + scanner.nextLine());
        } catch (Throwable th) {
            System.out.println("Client stopped!");
        }
    }
}

public class TestDynamicProgramNotFinishedAfterTestButShutDown extends StageTest<String> {

    public TestDynamicProgramNotFinishedAfterTestButShutDown() {
        super(TestDynamicProgramNotFinishedAfterTestButShutDownServer.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicTesting(() -> {

                TestedProgram server = new TestedProgram(
                    TestDynamicProgramNotFinishedAfterTestButShutDownServer.class);

                TestedProgram client = new TestedProgram(
                    TestDynamicProgramNotFinishedAfterTestButShutDownClient.class);

                String out1 = server.start();
                String out2 = client.start();
                if (!out1.equals("Server started!\n")
                    || !out2.equals("Client started!\n")) {
                    return CheckResult.wrong("");
                }

                return null;
            })
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        if (reply.contains("Server stopped!\n" +
            "java.util.NoSuchElementException: No line found")
            && reply.contains("Client stopped!")) {
            return CheckResult.correct();
        }
        return CheckResult.wrong("");
    }
}
