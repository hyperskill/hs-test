package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.dynamic.output.InfiniteLoopDetector;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.After;
import org.junit.Before;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class InfiniteLoopTestLine11Main {
    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 25; i++) {
                System.out.println("Line" + i);
            }
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

    boolean before;

    @Before
    public void before() {
        before = InfiniteLoopDetector.isCheckNoInputRequestsForLong();
        InfiniteLoopDetector.setCheckNoInputRequestsForLong(true);
    }

    @After
    public void after() {
        InfiniteLoopDetector.setCheckNoInputRequestsForLong(before);
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram test = new TestedProgram(InfiniteLoopTestLine11Main.class);
        test.start();
        return CheckResult.correct();
    }
}
