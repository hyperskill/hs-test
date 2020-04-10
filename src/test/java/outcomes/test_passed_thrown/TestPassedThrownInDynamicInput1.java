package outcomes.test_passed_thrown;

import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestPassedThrownInDynamicInput1Main {
    public static void main(String[] args) {
        System.out.print("1");
        new Scanner(System.in).next();
    }
}

public class TestPassedThrownInDynamicInput1 extends StageTest<Boolean> {

    public TestPassedThrownInDynamicInput1() {
        super(TestPassedThrownInDynamicInput1Main.class);
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
            new TestCase<Boolean>()
                .addInput(out -> {
                    if (out.equals("1")) {
                        throw new TestPassed();
                    }
                    return "2";
                }),

            new TestCase<Boolean>()
                .addInput(out -> {
                    if (out.equals("2")) {
                        throw new TestPassed();
                    }
                    return "1";
                })
        );
    }

    @Override
    public CheckResult check(String reply, Boolean attach) {
        return CheckResult.wrong("fail inside check");
    }
}
