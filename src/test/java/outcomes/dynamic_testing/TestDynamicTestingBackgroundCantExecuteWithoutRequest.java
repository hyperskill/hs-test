package outcomes.dynamic_testing;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Scanner;

class TestDynamicTestingBackgroundCantExecuteWithoutRequestMain {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(20);
        scanner.nextLine();
    }
}

public class TestDynamicTestingBackgroundCantExecuteWithoutRequest extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "Tested program is not waiting for the input (state == \"RUNNING\")"
    };

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundCantExecuteWithoutRequestMain.class);
        pr.startInBackground();
        pr.execute("123");
        return CheckResult.correct();
    }
}
