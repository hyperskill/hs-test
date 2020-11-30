package outcomes.separate_package.two_mains_1;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class Main1 {
    public static void main(String[] args) {
        System.out.print("Main1");
    }
}

class Main2 {
    public static void main(String[] args) {
        System.out.print("Main2");
    }
}

public class TwoMains1 extends StageTest {

    public TwoMains1() {
        super(Main1.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("Main1"), "");
    }

}
