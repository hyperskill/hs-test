package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorEmptyTestCasesMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorEmptyTestCases extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error during testing",
        "No tests found"
    };

    public UnexpectedErrorEmptyTestCases() {
        super(UnexpectedErrorEmptyTestCasesMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList();
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }

}
