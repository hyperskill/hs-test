package outcomes.parametrized_tests.correct;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestParametrizedArray extends StageTest {

    Object[][] data = {
        { new int[]{ 1, 2, 3 }, new int[]{ 2, 3, 4 } },
        { new int[]{ 2, 3, 4 }, new int[]{ 3, 4, 5 } },
        { new int[]{ 3, 4, 5 }, new int[]{ 4, 5, 6 } },
    };

    int c = 0;

    @DynamicTest(data = "data")
    CheckResult test1(int[] x, int[] y) {
        c++;
        if (c == x[0] && c + 1 == x[1] && c + 2 == x[2] && x[1] == y[0] && x[2] == y[1]) {
            return CheckResult.correct();
        }
        return CheckResult.wrong("");
    }

}
