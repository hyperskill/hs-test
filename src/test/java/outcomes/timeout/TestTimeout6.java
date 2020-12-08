package outcomes.timeout;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestTimeout6Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(scanner.nextInt());
        } catch (Exception ignored) { }
    }
}

public class TestTimeout6 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Error in test #3",

        "In this test, " +
        "the program is running for a long time, more than 300 milliseconds. " +
        "Most likely, the program has gone into an infinite loop."
    };

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(skipSlow);
    }

    @DynamicTest(timeLimit = 300)
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestTimeout6Main.class);
        pr.start();
        pr.execute("100");
        return CheckResult.correct();
    }

    @DynamicTest(timeLimit = 300)
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestTimeout6Main.class);
        pr.start();
        pr.execute("200");
        return CheckResult.correct();
    }

    @DynamicTest(timeLimit = 300)
    CheckResult test3() {
        TestedProgram pr = new TestedProgram(TestTimeout6Main.class);
        pr.start();
        pr.execute("400");
        return CheckResult.correct();
    }
}
