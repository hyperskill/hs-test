package outcomes;

import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestPassedThrownInDynamicTesting1Main {
    public static void main(String[] args) {
        System.out.print("1");
        new Scanner(System.in).next();
    }
}

public class TestPassedThrownInDynamicTesting1 extends StageTest<Boolean> {

    public TestPassedThrownInDynamicTesting1() {
        super(TestPassedThrownInDynamicTesting1Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #2\n\n" +
            "fail inside check");
        exception.expectMessage(not(containsString("Fatal error")));
    }

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicTesting1Main.class);
                String out = pr.start();
                if (out.equals("1")) {
                    throw new TestPassed();
                }
                pr.execute("2");
                return CheckResult.wrong("fail inside check");
            }),

            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicTesting1Main.class);
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
