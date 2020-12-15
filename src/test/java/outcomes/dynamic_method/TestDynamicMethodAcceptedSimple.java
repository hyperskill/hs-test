package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicMethodAcceptedSimpleMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Program started!");
        System.out.println("S1: " + scanner.nextLine());
        System.out.println("S2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodAcceptedSimple extends StageTest<String> {
    @DynamicTest
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            "outcomes.dynamic_method.TestDynamicMethodAcceptedSimpleMain");

        String out1 = pr.start();

        if (!out1.equals("Program started!\n")) {
            return CheckResult.wrong("");
        }

        String out3 = pr.execute("input1");
        if (!out3.equals("S1: input1\n")) {
            return CheckResult.wrong("");
        }

        String out5 = pr.execute("input2");
        if (!out5.equals("S2: input2\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
