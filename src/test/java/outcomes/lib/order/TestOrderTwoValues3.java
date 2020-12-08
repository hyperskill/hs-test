package outcomes.lib.order;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestOrderTwoValues3 extends StageTest {

    int x = 0;

    @DynamicTest(order = 3)
    CheckResult test1() {
        return new CheckResult(++x == 2, "");
    }

    @DynamicTest(order = 2)
    CheckResult test2() {
        return new CheckResult(++x == 1, "");
    }
}
