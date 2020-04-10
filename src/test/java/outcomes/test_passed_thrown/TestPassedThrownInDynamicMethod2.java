package outcomes.test_passed_thrown;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestPassedThrownInDynamicMethod2Main {
    public static void main(String[] args) {
        System.out.print("2");
        new Scanner(System.in).next();
    }
}

public class TestPassedThrownInDynamicMethod2 extends StageTest<Boolean> {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #");
        exception.expectMessage("\n\nfail inside check test1");
        exception.expectMessage(not(containsString("Fatal error")));
    }

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicMethod2Main.class);
        String out = pr.start();
        if (out.equals("1")) {
            throw new TestPassed();
        }
        pr.execute("2");
        return CheckResult.wrong("fail inside check test1");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicMethod2Main.class);
        String out = pr.start();
        if (out.equals("2")) {
            throw new TestPassed();
        }
        pr.execute("1");
        return CheckResult.wrong("fail inside check test2");
    }
}
