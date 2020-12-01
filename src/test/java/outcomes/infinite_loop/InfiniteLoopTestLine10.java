package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class InfiniteLoopTestLine10Main {
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
        }
    }
}

public class InfiniteLoopTestLine10 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Infinite loop detected.\n" +
        "Last 100 lines your program printed have 10 blocks of 10 lines of the same text.";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram test = new TestedProgram(InfiniteLoopTestLine10Main.class);
        test.start();
        return CheckResult.correct();
    }
}
