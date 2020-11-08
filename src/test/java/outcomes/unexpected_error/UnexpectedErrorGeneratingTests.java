package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorGeneratingTestsMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorGeneratingTests extends UnexpectedErrorTest {

    @ContainsMessage
    String m = "Unexpected error during testing";

    public UnexpectedErrorGeneratingTests() {
        super(UnexpectedErrorGeneratingTestsMain.class);
    }

    @Override
    public List<TestCase> generate() {
        System.out.println(1 / 0);
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
