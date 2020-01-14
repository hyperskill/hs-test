package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class TestCurrTestCaseMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print(s.nextInt());
    }
}

public class TestCurrTestCase extends StageTest<Integer> {

    public TestCurrTestCase() {
        super(TestCurrTestCaseMain.class);
    }

    static TestCase<Integer> test1;
    static TestCase<Integer> test2;

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
        TestCase testCase = StageTest.getCurrTestRun().getTestCase();
        if (reply.equals("1") && attach == 1 && testCase == test1
            || reply.equals("2") && attach == 2 && testCase == test2) {
            return CheckResult.TRUE;

        }
        throw new NullPointerException();
    }
}
