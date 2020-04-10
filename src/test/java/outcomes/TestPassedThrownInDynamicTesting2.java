package outcomes;

import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestPassedThrownInDynamicTesting2Main {
    public static void main(String[] args) {
        System.out.print("2");
        new Scanner(System.in).next();
    }
}

public class TestPassedThrownInDynamicTesting2 extends StageTest<Boolean> {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #1\n\n" +
            "fail inside check");
        exception.expectMessage(not(containsString("Fatal error")));
    }

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicTesting2Main.class);
                String out = pr.start();
                if (out.equals("1")) {
                    throw new TestPassed();
                }
                pr.execute("2");
                return CheckResult.wrong("fail inside check");
            }),

            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicTesting2Main.class);
                String out = pr.start();
                if (out.equals("2")) {
                    throw new TestPassed();
                }
                pr.execute("1");
                return CheckResult.wrong("fail inside check");
            })
        );
    }
}
