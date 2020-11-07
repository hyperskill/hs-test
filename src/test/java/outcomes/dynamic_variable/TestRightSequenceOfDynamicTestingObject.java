package outcomes.dynamic_variable;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class TestRightSequenceOfDynamicTestingObject extends StageTest {

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
    DynamicTesting test1 = () -> new CheckResult(++x == 1, "");

    @DynamicTestingMethod
    DynamicTesting test2 = () -> new CheckResult(++x == 2, "");

    @DynamicTestingMethod
    DynamicTesting test3 = () -> new CheckResult(++x == 3, "");

    @DynamicTestingMethod
    DynamicTesting test4 = () -> new CheckResult(++x == 4, "");

    @DynamicTestingMethod
    DynamicTesting test5 = () -> new CheckResult(++x == 5, "");

    @DynamicTestingMethod
    DynamicTesting test6 = () -> new CheckResult(++x == 6, "");

    @DynamicTestingMethod
    DynamicTesting test7 = () -> new CheckResult(++x == 7, "");

    @DynamicTestingMethod
    DynamicTesting test8 = () -> new CheckResult(++x == 8, "");

    @DynamicTestingMethod
    DynamicTesting test9 = () -> new CheckResult(++x == 9, "");

    @DynamicTestingMethod
    DynamicTesting test10 = () -> new CheckResult(++x == 10, "");

    @DynamicTestingMethod
    DynamicTesting test11 = () -> new CheckResult(++x == 11, "");

    @DynamicTestingMethod
    DynamicTesting test12 = () -> new CheckResult(++x == 12, "");

    @DynamicTestingMethod
    DynamicTesting test13 = () -> new CheckResult(++x == 13, "");

    @DynamicTestingMethod
    DynamicTesting test14 = () -> new CheckResult(++x == 14, "");

    @DynamicTestingMethod
    DynamicTesting test15 = () -> CheckResult.wrong(x + " == x");
}
