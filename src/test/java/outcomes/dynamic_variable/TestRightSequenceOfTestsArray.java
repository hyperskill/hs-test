package outcomes.dynamic_variable;

import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestRightSequenceOfTestsArray extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #15\n" +
        "\n" +
        "14 == x";

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
