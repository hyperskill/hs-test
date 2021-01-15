package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestDynamicMethodProgramNotFinishedAfterTestButShutDownMain {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("S1: " + scanner.nextLine());
            System.out.println("S2: " + scanner.nextLine());
        } catch (Throwable th) {
            System.out.println("Server stopped!");
        }
    }
}

public class TestDynamicMethodProgramNotFinishedAfterTestButShutDown extends UserErrorTest<String> {

    @ContainsMessage
    String s =
        "Error in test #1\n" +
        "\n" +
        "Program ran out of input. You tried to read more, than expected.";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodProgramNotFinishedAfterTestButShutDownMain.class);

        String out1 = server.start();
        if (!out1.equals("Server started!\n")) {
            return CheckResult.wrong("");
        }

        return null;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        if (reply.contains("Server stopped!\n")) {
            return CheckResult.correct();
        }
        return CheckResult.wrong("");
    }
}
