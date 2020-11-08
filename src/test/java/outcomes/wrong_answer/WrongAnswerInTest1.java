package outcomes.wrong_answer;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class WrongAnswerInTest1Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class WrongAnswerInTest1 extends UserErrorTest {

    @ContainsMessage
    String m = "Wrong answer in test #1";

    public WrongAnswerInTest1() {
        super(WrongAnswerInTest1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.wrong("");
    }
}
