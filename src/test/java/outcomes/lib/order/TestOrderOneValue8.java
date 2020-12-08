package outcomes.lib.order;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestOrderOneValue8 extends StageTest {

    int x = 0;

    @DynamicTestingMethod
    CheckResult test1() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTest(order = 0)
    CheckResult test2() {
        return new CheckResult(++x == 2, "");
    }
}
