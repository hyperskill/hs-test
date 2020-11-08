package outcomes.timeout;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestTimeout2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(scanner.nextInt());
        } catch (Exception ignored) { }
    }
}

public class TestTimeout2 extends UserErrorTest {

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

    public TestTimeout2() {
        super(TestTimeout2Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setInput("100").setTimeLimit(300),
            new TestCase().setInput("200").setTimeLimit(300),
            new TestCase().setInput("400").setTimeLimit(300)
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
