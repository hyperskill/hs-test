package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

class UnexpectedErrorNotGeneratingTestsMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorNotGeneratingTests extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error during testing",
        "No tests found"
    };

    public UnexpectedErrorNotGeneratingTests() {
        super(UnexpectedErrorNotGeneratingTestsMain.class);
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }

}
