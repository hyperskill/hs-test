package outcomes.test_passed_thrown;

import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class TestPassedThrownInCheck2Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class TestPassedThrownInCheck2 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String m =
        "Wrong answer in test #2\n\n" +
        "test is not passed attach false";

    public TestPassedThrownInCheck2() {
        super(TestPassedThrownInCheck2Main.class);
    }

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>().setAttach(true),
            new TestCase<Boolean>().setAttach(false)
        );
    }

    @Override
    public CheckResult check(String reply, Boolean attach) {
        if (attach) {
            throw new TestPassed();
        }
        return new CheckResult(false, "test is not passed attach false");
    }
}
