package outcomes.lib.order;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestOrderTwoValues5 extends StageTest {

    int x = 0;

    @DynamicTest(order = -5)
    CheckResult test1() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTest(order = -1)
    CheckResult test2() {
        return new CheckResult(++x == 2, "");
    }

    @DynamicTest
    CheckResult test3() {
        return new CheckResult(++x == 3, "");
    }
}
