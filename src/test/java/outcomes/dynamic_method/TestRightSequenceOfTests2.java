package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestRightSequenceOfTests2 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #15\n" +
        "\n" +
        "x == 14";

    int x = 0;

    @DynamicTestingMethod
    CheckResult test1_SomeName() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTestingMethod
    CheckResult test2_ABC() {
        return new CheckResult(++x == 2, "");
    }

    @DynamicTestingMethod
    CheckResult test3_QWERTY() {
        return new CheckResult(++x == 3, "");
    }

    @DynamicTestingMethod
    CheckResult test4_RANDOMAFTER() {
        return new CheckResult(++x == 4, "");
    }

    @DynamicTestingMethod
    CheckResult test5_STR() {
        return new CheckResult(++x == 5, "");
    }

    @DynamicTestingMethod
    CheckResult test6_MethodName() {
        return new CheckResult(++x == 6, "");
    }

    @DynamicTestingMethod
    CheckResult test7withoutUnderscore() {
        return new CheckResult(++x == 7, "");
    }

    @DynamicTestingMethod
    CheckResult test8abc() {
        return new CheckResult(++x == 8, "");
    }

    @DynamicTestingMethod
    CheckResult test9methodName() {
        return new CheckResult(++x == 9, "");
    }

    @DynamicTestingMethod
    CheckResult test10_Ten() {
        return new CheckResult(++x == 10, "");
    }

    @DynamicTestingMethod
    CheckResult test11eleven() {
        return new CheckResult(++x == 11, "");
    }

    @DynamicTestingMethod
    CheckResult test12_twelve() {
        return new CheckResult(++x == 12, "");
    }

    @DynamicTestingMethod
    CheckResult test13_thirteen() {
        return new CheckResult(++x == 13, "");
    }

    @DynamicTestingMethod
    CheckResult test14_fourteen() {
        return new CheckResult(++x == 14, "");
    }

    @DynamicTestingMethod
    CheckResult test15_fifteen() {
        return CheckResult.wrong("x == " + x);
    }
}
