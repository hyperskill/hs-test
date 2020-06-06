package outcomes.dynamic_testing;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

class TestDynamicTestingBackgroundCantExecuteWithoutRequestMain {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(20);
        scanner.nextLine();
    }
}

public class TestDynamicTestingBackgroundCantExecuteWithoutRequest extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #1, " +
            "please send the report to support@hyperskill.org");

        exception.expectMessage("Tested program is not waiting for the input (state == \"RUNNING\")");
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(
            TestDynamicTestingBackgroundCantExecuteWithoutRequestMain.class);
        pr.startInBackground();
        pr.execute("123");
        return CheckResult.correct();
    }
}
