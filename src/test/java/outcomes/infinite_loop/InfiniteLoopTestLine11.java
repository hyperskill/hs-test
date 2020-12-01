package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class InfiniteLoopTestLine11Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Line1");
            System.out.println("Line2");
            System.out.println("Line3");
            System.out.println("Line4");
            System.out.println("Line5");
            System.out.println("Line6");
            System.out.println("Line7");
            System.out.println("Line8");
            System.out.println("Line9");
            System.out.println("Line10");
            System.out.println("Line11");
        }
    }
}

public class InfiniteLoopTestLine11 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Infinite loop detected.\n" +
        "No input request for the last 500 lines being printed.";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram test = new TestedProgram(InfiniteLoopTestLine11Main.class);
        test.start();
        return CheckResult.correct();
    }
}
