package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestCurrTestCase extends BaseStageTest<Integer> {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int test = s.nextInt();
        TestCase testCase = BaseStageTest.getCurrTestRun().getTestCase();
        if (test == 1 && testCase != test1
            || test == 2 && testCase != test2) {
            throw new NullPointerException();
        }
    }

    public TestCurrTestCase() {
        super(TestCurrTestCase.class);
    }

    private static TestCase<Integer> test1;
    private static TestCase<Integer> test2;

    @Override
    public List<TestCase<Integer>> generate() {
        test1 = new TestCase<Integer>().setInput("1").setAttach(1);
        test2 = new TestCase<Integer>().setInput("2").setAttach(2);

        return Arrays.asList(
            test1, test2
        );
    }

    @Override
    public CheckResult check(String reply, Integer attach) {
        TestCase testCase = BaseStageTest.getCurrTestRun().getTestCase();
        if (attach == 1 && testCase != test1
            || attach == 2 && testCase != test2) {
            throw new NullPointerException();
        }
        return CheckResult.TRUE;
    }
}
