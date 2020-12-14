package outcomes.parametrized_tests.correct;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestParametrizedObjects extends StageTest {

    Object[][] arr = {
        {1, 2},
        {2, 3},
        {3, 4},
        {4, 5},
        {5, 6}
    };

    int counter = 0;

    @DynamicTest(data = "arr")
    CheckResult test(int a, int b) {
        counter++;
        System.out.println(a + " " + b);
        return new CheckResult(counter == a && counter + 1 == b, "");
    }

}
