package outcomes.lib.order;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class TestOrderComplexMain {
    public static void main(String[] args) {

    }
}

public class TestOrderComplex extends StageTest {

    public TestOrderComplex() {
        super(TestOrderComplexMain.class);
    }

    int x = 0;

    @DynamicTest(order = -5)
    CheckResult test0() {
        return new CheckResult(++x == 4, "test0");
    }

    @DynamicTest(order = -1)
    CheckResult test1() {
        return new CheckResult(++x == 9, "test1");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        return new CheckResult(++x == 10, "test2");
    }

    @DynamicTest
    CheckResult test3() {
        return new CheckResult(++x == 11, "test3");
    }

    @DynamicTestingMethod
    CheckResult test4() {
        return new CheckResult(++x == 12, "test4");
    }

    @DynamicTest
    CheckResult test5() {
        return new CheckResult(++x == 13, "test5");
    }

    @DynamicTest(order = -3)
    DynamicTesting[] test6 = {
        () -> new CheckResult(++x == 5, "test6 1"),
        () -> new CheckResult(++x == 6, "test6 2"),
    };

    @DynamicTest(order = -2)
    List<DynamicTesting> test7 = Arrays.asList(
        () -> new CheckResult(++x == 7, "test7 1"),
        () -> new CheckResult(++x == 8, "test7 2")
    );

    @DynamicTest(order = 1)
    DynamicTesting test8 = () -> new CheckResult(++x == 14, "test8");

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<>(),
            new TestCase<>().setCheckFunc(this::test9),
            new TestCase<>().setDynamicTesting(() -> new CheckResult(++x == 3, "generate"))
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(++x == 1, "check");
    }

    CheckResult test9(String reply, Object attach) {
        return new CheckResult(++x == 2, "test9");
    }

    @DynamicTest(order = 10)
    CheckResult test10() {
        return new CheckResult(++x == 15, "test10");
    }
}
