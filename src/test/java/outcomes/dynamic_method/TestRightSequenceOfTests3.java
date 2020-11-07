package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class TestRightSequenceOfTests3 extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #15\n" +
                "\n" +
                "x == 14"
        );

        exception.expectMessage(not(containsString("Unexpected error")));
    }

    int x = 0;

    @DynamicTestingMethod
    CheckResult fixed_12_someNum1_SomeName() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum2_ABC() {
        return new CheckResult(++x == 2, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum3_QWERTY() {
        return new CheckResult(++x == 3, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum4_RANDOMAFTER() {
        return new CheckResult(++x == 4, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum5_STR() {
        return new CheckResult(++x == 5, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum6_MethodName() {
        return new CheckResult(++x == 6, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum7withoutUnderscore() {
        return new CheckResult(++x == 7, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum8abc() {
        return new CheckResult(++x == 8, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum9methodName() {
        return new CheckResult(++x == 9, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum10_Ten() {
        return new CheckResult(++x == 10, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum11eleven() {
        return new CheckResult(++x == 11, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum12_twelve() {
        return new CheckResult(++x == 12, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum13_thirteen() {
        return new CheckResult(++x == 13, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum14_fourteen() {
        return new CheckResult(++x == 14, "");
    }

    @DynamicTestingMethod
    CheckResult fixed_12_someNum15_fifteen() {
        return CheckResult.wrong("x == " + x);
    }
}
