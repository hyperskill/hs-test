package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorDuringCheckingMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorDuringChecking extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Unexpected error in test #1";

    public UnexpectedErrorDuringChecking() {
        super(UnexpectedErrorDuringCheckingMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        System.out.println(1 / 0);
        return CheckResult.correct();
    }
}
