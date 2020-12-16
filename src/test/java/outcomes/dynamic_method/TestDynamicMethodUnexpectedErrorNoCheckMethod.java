package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Scanner;

class TestDynamicMethodUnexpectedErrorNoCheckMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
        System.out.println("S2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodUnexpectedErrorNoCheckMethod extends UnexpectedErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "UnexpectedError: Can't check result: override \"check\" method"
    };

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestDynamicMethodUnexpectedErrorNoCheckMethodMain.class);

        main.start();
        main.execute("main");
        main.execute("main2");
        return null;
    }
}
