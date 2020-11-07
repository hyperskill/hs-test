package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class TestRightSequenceOfTests4 extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #70\n" +
                "\n" +
                "x == 69"
        );

        exception.expectMessage(not(containsString("Unexpected error")));
    }

    int x = 0;

    @DynamicTestingMethod
    CheckResult test_0_test_0_test_0() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_0_test_1() {
        return new CheckResult(++x == 2, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_0_test_2() {
        return new CheckResult(++x == 3, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_0_test_10() {
        return new CheckResult(++x == 4, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_0_test_123() {
        return new CheckResult(++x == 5, "");
    }


    @DynamicTestingMethod
    CheckResult test_0_test_1_test_0() {
        return new CheckResult(++x == 6, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_1_test_1() {
        return new CheckResult(++x == 7, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_1_test_2() {
        return new CheckResult(++x == 8, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_1_test_10() {
        return new CheckResult(++x == 9, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_1_test_123() {
        return new CheckResult(++x == 10, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_2_test_0() {
        return new CheckResult(++x == 11, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_2_test_1() {
        return new CheckResult(++x == 12, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_2_test_2() {
        return new CheckResult(++x == 13, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_2_test_10() {
        return new CheckResult(++x == 14, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_2_test_123() {
        return new CheckResult(++x == 15, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_10_test_0() {
        return new CheckResult(++x == 16, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_10_test_1() {
        return new CheckResult(++x == 17, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_10_test_2() {
        return new CheckResult(++x == 18, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_10_test_10() {
        return new CheckResult(++x == 19, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_10_test_123() {
        return new CheckResult(++x == 20, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_123_test_0() {
        return new CheckResult(++x == 21, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_123_test_1() {
        return new CheckResult(++x == 22, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_123_test_2() {
        return new CheckResult(++x == 23, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_123_test_10() {
        return new CheckResult(++x == 24, "");
    }

    @DynamicTestingMethod
    CheckResult test_0_test_123_test_123() {
        return new CheckResult(++x == 25, "");
    }

    @DynamicTestingMethod
    CheckResult test_1_test_123_test_0() {
        return new CheckResult(++x == 26, "");
    }

    @DynamicTestingMethod
    CheckResult test_1_test_123_test_2() {
        return new CheckResult(++x == 27, "");
    }

    @DynamicTestingMethod
    CheckResult test_1_test_123_test_10() {
        return new CheckResult(++x == 28, "");
    }

    @DynamicTestingMethod
    CheckResult test_1_test_123_test_123() {
        return new CheckResult(++x == 29, "");
    }

    @DynamicTestingMethod
    CheckResult test_2_test_123_test_0() {
        return new CheckResult(++x == 30, "");
    }

    @DynamicTestingMethod
    CheckResult test_2_test_123_test_1() {
        return new CheckResult(++x == 31, "");
    }

    @DynamicTestingMethod
    CheckResult test_2_test_123_test_2() {
        return new CheckResult(++x == 32, "");
    }

    @DynamicTestingMethod
    CheckResult test_2_test_123_test_10() {
        return new CheckResult(++x == 33, "");
    }

    @DynamicTestingMethod
    CheckResult test_2_test_123_test_123() {
        return new CheckResult(++x == 34, "");
    }

    @DynamicTestingMethod
    CheckResult test_10_test_123_test_0() {
        return new CheckResult(++x == 35, "");
    }

    @DynamicTestingMethod
    CheckResult test_10_test_123_test_1() {
        return new CheckResult(++x == 36, "");
    }

    @DynamicTestingMethod
    CheckResult test_10_test_123_test_2() {
        return new CheckResult(++x == 37, "");
    }

    @DynamicTestingMethod
    CheckResult test_10_test_123_test_10() {
        return new CheckResult(++x == 38, "");
    }

    @DynamicTestingMethod
    CheckResult test_10_test_123_test_123() {
        return new CheckResult(++x == 39, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_test_123_test_0() {
        return new CheckResult(++x == 40, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_test_123_test_1() {
        return new CheckResult(++x == 41, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_test_123_test_2() {
        return new CheckResult(++x == 42, "");
    }


    @DynamicTestingMethod
    CheckResult test_123_test_123_test_10() {
        return new CheckResult(++x == 43, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_test_123_test_123() {
        return new CheckResult(++x == 44, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_test_123_yest_123() {
        return new CheckResult(++x == 45, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_test_123_zest_123() {
        return new CheckResult(++x == 46, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_yest_123_test_123() {
        return new CheckResult(++x == 47, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_yest_123_yest_123() {
        return new CheckResult(++x == 48, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_yest_123_zest_123() {
        return new CheckResult(++x == 49, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_zest_123_test_123() {
        return new CheckResult(++x == 50, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_zest_123_yest_123() {
        return new CheckResult(++x == 51, "");
    }

    @DynamicTestingMethod
    CheckResult test_123_zest_123_zest_123() {
        return new CheckResult(++x == 52, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_test_123_test_123() {
        return new CheckResult(++x == 53, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_test_123_yest_123() {
        return new CheckResult(++x == 54, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_test_123_zest_123() {
        return new CheckResult(++x == 55, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_yest_123_test_123() {
        return new CheckResult(++x == 56, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_yest_123_yest_123() {
        return new CheckResult(++x == 57, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_yest_123_zest_123() {
        return new CheckResult(++x == 58, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_zest_123_test_123() {
        return new CheckResult(++x == 59, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_zest_123_yest_123() {
        return new CheckResult(++x == 60, "");
    }

    @DynamicTestingMethod
    CheckResult yest_123_zest_123_zest_123() {
        return new CheckResult(++x == 61, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_test_123_test_123() {
        return new CheckResult(++x == 62, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_test_123_yest_123() {
        return new CheckResult(++x == 63, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_test_123_zest_123() {
        return new CheckResult(++x == 64, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_yest_123_test_123() {
        return new CheckResult(++x == 65, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_yest_123_yest_123() {
        return new CheckResult(++x == 66, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_yest_123_zest_123() {
        return new CheckResult(++x == 67, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_zest_123_test_123() {
        return new CheckResult(++x == 68, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_zest_123_yest_123() {
        return new CheckResult(++x == 69, "");
    }

    @DynamicTestingMethod
    CheckResult zest_123_zest_123_zest_123() {
        return CheckResult.wrong("x == " + x);
    }

}
