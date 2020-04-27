package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class TestRightSequenceOfTests extends StageTest {

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

        exception.expectMessage(not(containsString("Fatal error")));
    }

    int x = 0;

    @DynamicTestingMethod
    CheckResult test1() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        return new CheckResult(++x == 2, "");
    }

    @DynamicTestingMethod
    CheckResult test3() {
        return new CheckResult(++x == 3, "");
    }

    @DynamicTestingMethod
    CheckResult test4() {
        return new CheckResult(++x == 4, "");
    }

    @DynamicTestingMethod
    CheckResult test5() {
        return new CheckResult(++x == 5, "");
    }

    @DynamicTestingMethod
    CheckResult test6() {
        return new CheckResult(++x == 6, "");
    }

    @DynamicTestingMethod
    CheckResult test7() {
        return new CheckResult(++x == 7, "");
    }

    @DynamicTestingMethod
    CheckResult test8() {
        return new CheckResult(++x == 8, "");
    }

    @DynamicTestingMethod
    CheckResult test9() {
        return new CheckResult(++x == 9, "");
    }

    @DynamicTestingMethod
    CheckResult test10() {
        return new CheckResult(++x == 10, "");
    }

    @DynamicTestingMethod
    CheckResult test11() {
        return new CheckResult(++x == 11, "");
    }

    @DynamicTestingMethod
    CheckResult test12() {
        return new CheckResult(++x == 12, "");
    }

    @DynamicTestingMethod
    CheckResult test13() {
        return new CheckResult(++x == 13, "");
    }

    @DynamicTestingMethod
    CheckResult test14() {
        return new CheckResult(++x == 14, "");
    }

    @DynamicTestingMethod
    CheckResult test15() {
        return CheckResult.wrong("x == " + x);
    }
}
