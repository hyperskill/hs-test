package outcomes.test_passed_thrown;

import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class TestPassedThrownInCheck1Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class TestPassedThrownInCheck1 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n\n" +
        "test is not passed attach true";

    public TestPassedThrownInCheck1() {
        super(TestPassedThrownInCheck1Main.class);
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
        if (!attach) {
            throw new TestPassed();
        }
        return new CheckResult(false, "test is not passed attach true");
    }
}
