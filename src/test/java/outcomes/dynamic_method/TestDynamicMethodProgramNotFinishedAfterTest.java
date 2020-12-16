package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicMethodProgramNotFinishedAfterTestMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
        System.out.println("S2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodProgramNotFinishedAfterTest extends StageTest<String> {
    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestDynamicMethodProgramNotFinishedAfterTestMain.class);

        String out1 = main.start();
        if (!out1.equals("Server started!\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
