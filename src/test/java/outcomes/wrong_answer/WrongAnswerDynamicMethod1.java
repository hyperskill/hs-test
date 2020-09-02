package outcomes.wrong_answer;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class WrongAnswerDynamicMethod1Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        scanner.nextLine();
    }
}

public class WrongAnswerDynamicMethod1 extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #1");
        exception.expectMessage("WA TEST 1");
        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(WrongAnswerDynamicMethod1Main.class);
        pr.start();
        return CheckResult.wrong("WA TEST 1");
    }
}
