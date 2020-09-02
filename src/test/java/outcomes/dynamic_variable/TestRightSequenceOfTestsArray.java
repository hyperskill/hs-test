package outcomes.dynamic_variable;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class TestRightSequenceOfTestsArray extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #15\n" +
                "\n" +
                "14 == x"
        );

        exception.expectMessage(not(containsString("Unexpected error")));
    }

    int x = 0;

    @DynamicTestingMethod
    DynamicTesting[] tests = new DynamicTesting[]{
        () -> new CheckResult(++x == 1, ""),
        () -> new CheckResult(++x == 2, ""),
        () -> new CheckResult(++x == 3, ""),
        () -> new CheckResult(++x == 4, ""),
        () -> new CheckResult(++x == 5, ""),
        () -> new CheckResult(++x == 6, ""),
        () -> new CheckResult(++x == 7, ""),
        () -> new CheckResult(++x == 8, ""),
        () -> new CheckResult(++x == 9, ""),
        () -> new CheckResult(++x == 10, ""),
        () -> new CheckResult(++x == 11, ""),
        () -> new CheckResult(++x == 12, ""),
        () -> new CheckResult(++x == 13, ""),
        () -> new CheckResult(++x == 14, ""),
        () -> CheckResult.wrong(x + " == x")
    };
}
