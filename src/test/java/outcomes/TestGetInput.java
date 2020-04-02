package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestGetInputMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print(s.nextLine());
    }
}

public class TestGetInput extends StageTest<String> {

    public TestGetInput() {
        super(TestGetInputMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {

        List<TestCase<String>> tests = Arrays.asList(
            new TestCase<String>().setInput("1234"),
            new TestCase<String>().setInput("4321")
        );

        for (TestCase<String> test : tests) {
            test.setAttach(test.getInput());
        }

        return tests;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
