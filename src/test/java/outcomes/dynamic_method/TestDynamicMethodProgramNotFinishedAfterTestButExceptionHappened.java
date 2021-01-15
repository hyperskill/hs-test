package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappenedMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
        System.out.println("S2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappened extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Program run out of input. You tried to read more, than expected.";

    @DynamicTest
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestDynamicMethodProgramNotFinishedAfterTestButExceptionHappenedMain.class);

        String out = main.start();
        if (!out.equals("Server started!\n")) {
            return CheckResult.wrong("");
        }

        return null;
    }
}
