package outcomes;

import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class Success extends BaseStageTest<String> {

    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    public Success() {
        super(Success.class);
    }

    String succ = "123";

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setAttach(succ)
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        if (attach == null) {
            System.out.println(0 / 0);
        }
        return CheckResult.TRUE;
    }
}
