package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

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

public class TestDynamicMethodProgramNotFinishedAfterTestButShutDown extends StageTest<String> {
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
