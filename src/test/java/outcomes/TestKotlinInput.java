package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.v7.dynamic.input.KotlinInput.readLine;


class TestKotlinInputMain {
    public static void main(String[] args) throws IOException {
        System.out.print(readLine());
    }
}

public class TestKotlinInput extends BaseStageTest<String> {

    public TestKotlinInput() {
        super(TestKotlinInputMain.class);
        needReloadClass = false;
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
        return new CheckResult(reply.equals(attach));
    }
}
