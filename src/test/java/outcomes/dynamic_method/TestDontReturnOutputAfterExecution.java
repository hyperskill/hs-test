package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestDontReturnOutputAfterExecutionMain {
    public static void main(String[] args) {
        System.out.println("Initial text");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.println("1 to 2");
        scanner.nextLine();

        System.out.println("2 to 3");
        scanner.nextLine();

        System.out.println("3 to 4");
        scanner.nextLine();
    }
}

public class TestDontReturnOutputAfterExecution extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestDontReturnOutputAfterExecutionMain.class);

        main.setReturnOutputAfterExecution(false);

        String out = main.start();
        if (out.length() != 0) {
            return CheckResult.wrong("Output should be empty");
        }

        out = main.getOutput();
        if (!out.equals("Initial text\n")) {
            return CheckResult.wrong("Output is wrong");
        }

        for (int i = 0; i < 2; i++) {
            if (main.execute("").length() != 0) {
                return CheckResult.wrong("Output should be empty");
            }
        }

        out = main.getOutput();
        if (!out.equals("1 to 2\n2 to 3\n")) {
            return CheckResult.wrong("Output is wrong");
        }


        main.setReturnOutputAfterExecution(true);

        if (!main.execute("").equals("3 to 4\n")) {
            return CheckResult.wrong("Output should not be empty");
        }

        if (main.getOutput().length() != 0) {
            return CheckResult.wrong(
                "getOutput() should return an empty string at the end");
        }

        return CheckResult.correct();
    }
}
