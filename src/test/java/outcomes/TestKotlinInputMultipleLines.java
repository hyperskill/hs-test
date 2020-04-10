package outcomes;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.dynamic.input.KotlinInput.readLine;

class TestKotlinInputMultipleLinesMain {
    public static void main(String[] args) throws IOException {
        System.out.println(readLine());
        System.out.println(readLine());
        System.out.println(readLine());
    }
}

public class TestKotlinInputMultipleLines extends StageTest<String> {

    public TestKotlinInputMultipleLines() {
        super(TestKotlinInputMultipleLinesMain.class);
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
        return new CheckResult(reply.equals(attach), "");
    }
}
