package outcomes.lib;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class TestCleanTextMain {
    public static void main(String[] args) {
        System.out.print("123\n234\r\n345\r456");
    }
}

public class TestCleanText extends StageTest {

    public TestCleanText() {
        super(TestCleanTextMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("123\n234\n345\n456"), "");
    }
}
