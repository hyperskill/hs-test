package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Random;

class InfiniteLoopTestCharMain {
    public static void main(String[] args) {
        Random r = new Random();
        while (true) {
            System.out.println("Long Line Long Line Long Line" + r.nextInt());
        }
    }
}

public class InfiniteLoopTestChar extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Infinite loop detected.\n" +
        "No input request for the last 5000 characters being printed.";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram test = new TestedProgram(InfiniteLoopTestCharMain.class);
        test.start();
        return CheckResult.correct();
    }
}
