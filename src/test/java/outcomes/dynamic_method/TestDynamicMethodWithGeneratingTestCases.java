package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class TestDynamicMethodWithGeneratingTestCases extends StageTest {

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<>().setDynamicTesting(CheckResult::correct)
        );
    }

    @DynamicTestingMethod
    public CheckResult test() {
        return CheckResult.correct();
    }

}
