package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class InfiniteLoopTestLine2Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Line1");
            System.out.println("Line2");
        }
    }
}

public class InfiniteLoopTestLine2 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Infinite loop detected.\n" +
        "Last 100 lines your program printed have 50 blocks of 2 lines of the same text.";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram test = new TestedProgram(InfiniteLoopTestLine2Main.class);
        test.start();
        return CheckResult.correct();
    }
}
