package outcomes.parametrized_tests.correct;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestParametrizedMultipleDataProviders2 extends StageTest {

    Object[][] arr() {
        return new Object[][] {
            {7, 8},
            {8, 9},
        };
    }

    Object[][] arr2() {
        return new Object[][] {
            {1, "Test 1"},
            {2, "Test 2"},
            {3, "Test 3"},
            {4, "Test 4"},
            {5, "Test 5"},
            {6, "Test 6"},
        };
    }

    int counter = 0;

    @DynamicTest(data = "arr2")
    CheckResult test(int a, String b) {
        counter++;
        System.out.println(a + " " + b);
        return new CheckResult(
            counter == a &&
                ("Test " + counter).equals(b), "");
    }

    @DynamicTest(data = "arr")
    CheckResult test2(int a, int b) {
        counter++;
        System.out.println(a + " " + b);
        return new CheckResult(counter == a && counter + 1 == b, "");
    }

}
