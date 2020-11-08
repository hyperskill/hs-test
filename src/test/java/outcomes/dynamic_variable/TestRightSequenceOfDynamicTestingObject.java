package outcomes.dynamic_variable;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestRightSequenceOfDynamicTestingObject extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #15\n" +
        "\n" +
        "14 == x";

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
