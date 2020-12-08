package outcomes.lib.order;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestOrderTwoValues1 extends StageTest {

    int x = 0;

    @DynamicTest(order = 1)
    CheckResult test1() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTest(order = 2)
    CheckResult test2() {
        return new CheckResult(++x == 2, "");
    }
}
