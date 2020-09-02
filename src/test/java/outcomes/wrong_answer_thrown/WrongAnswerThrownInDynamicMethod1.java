package outcomes.wrong_answer_thrown;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class WrongAnswerThrownInDynamicMethod1Main {
    public static void main(String[] args) {
        System.out.print("1");
        new Scanner(System.in).next();
    }
}

public class WrongAnswerThrownInDynamicMethod1 extends StageTest<Boolean> {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #");
        exception.expectMessage("\n\nAdd input test 1");
        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(
            WrongAnswerThrownInDynamicMethod1Main.class);
        String out = pr.start();
        if (out.equals("1")) {
            throw new WrongAnswer("Add input test 1");
        }
        pr.execute("2");
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(
            WrongAnswerThrownInDynamicMethod1Main.class);
        String out = pr.start();
        if (out.equals("2")) {
            throw new WrongAnswer("Add input test 2");
        }
        pr.execute("1");
        return CheckResult.correct();
    }
}
