package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class SuccessMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class Success extends StageTest<String> {

    public Success() {
        super(SuccessMain.class);
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
        return CheckResult.correct();
    }
}
