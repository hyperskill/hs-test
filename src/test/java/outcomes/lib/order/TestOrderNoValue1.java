package outcomes.lib.order;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestOrderNoValue1 extends StageTest {

    int x = 0;

    @DynamicTest
    CheckResult test1() {
        return new CheckResult(++x == 1, "");
    }

    @DynamicTest
    CheckResult test2() {
        return new CheckResult(++x == 2, "");
    }
}
