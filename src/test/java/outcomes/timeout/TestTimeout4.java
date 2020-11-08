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
import java.util.concurrent.TimeUnit;

import static org.hyperskill.hstest.testing.ExecutionOptions.skipSlow;

class TestTimeout4Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Thread.sleep(scanner.nextInt());
        } catch (Exception ignored) { }
    }
}

public class TestTimeout4 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Error in test #2",

        "In this test, " +
        "the program is running for a long time, more than 2 seconds. " +
        "Most likely, the program has gone into an infinite loop."
    };

    @BeforeClass
    public static void stopSlow() {
        Assume.assumeFalse(skipSlow);
    }

    public TestTimeout4() {
        super(TestTimeout1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setInput("1500").setTimeLimit(2, TimeUnit.SECONDS),
            new TestCase().setInput("2500").setTimeLimit(2, TimeUnit.SECONDS)
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
