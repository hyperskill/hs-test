package outcomes.parametrized_tests.correct;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

public class TestParametrizedStrings extends StageTest {

    Object[] data = {
        "123",
        "234",
        "345"
    };

    int counter = 0;

    @DynamicTest(data = "data")
    CheckResult test1(String y) {
        System.out.println(y);
        return new CheckResult(y.equals(data[counter++]), "");
    }

}
