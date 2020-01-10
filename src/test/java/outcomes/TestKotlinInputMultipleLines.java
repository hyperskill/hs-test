package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.v7.dynamic.input.KotlinInput.readLine;


class TestKotlinInputMultipleLinesMain {
    public static void main(String[] args) throws IOException {
        System.out.println(readLine());
        System.out.println(readLine());
        System.out.println(readLine());
    }
}

public class TestKotlinInputMultipleLines extends BaseStageTest<String> {

    public TestKotlinInputMultipleLines() {
        super(TestKotlinInputMultipleLinesMain.class);
        needReloadClass = false;
    }

    @Override
    public List<TestCase<String>> generate() {

        List<TestCase<String>> tests = Arrays.asList(
            new TestCase<String>().setInput("1234\n2345\n3456\n"),
            new TestCase<String>().setInput("4321\n5432\n6543\n")
        );

        for (TestCase<String> test : tests) {
            test.setAttach(test.getInput());
        }

        return tests;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach));
    }
}
