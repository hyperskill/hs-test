package outcomes.lib.order;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestOrderMethodsAndVariables extends StageTest {

    int x = 0;

    @DynamicTest(order = -5)
    CheckResult test1() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTest(order = -1)
    CheckResult test2() {
        return new CheckResult(++x == 4, "");
    }

    @DynamicTest
    CheckResult test3() {
        return new CheckResult(++x == 5, "");
    }

    @DynamicTest(order = -3)
    DynamicTesting[] test4 = {
        () -> new CheckResult(++x == 2, ""),
        () -> new CheckResult(++x == 3, ""),
    };
}
