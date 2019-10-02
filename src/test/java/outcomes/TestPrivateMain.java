package outcomes;

import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class TestPrivateMain extends BaseStageTest {

    private static void main(String[] args) {
        System.out.print("Hello World");
    }

    public TestPrivateMain() {
        super(TestPrivateMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("Hello World"));
    }
}
