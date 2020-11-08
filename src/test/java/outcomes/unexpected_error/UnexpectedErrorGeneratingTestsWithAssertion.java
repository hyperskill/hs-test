package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorGeneratingTestsWithAssertionMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorGeneratingTestsWithAssertion extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Unexpected error during testing";

    public UnexpectedErrorGeneratingTestsWithAssertion() {
        super(UnexpectedErrorGeneratingTestsWithAssertionMain.class);
    }

    @Override
    public List<TestCase> generate() {
        assert false;
        return Arrays.asList(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
