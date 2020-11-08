package outcomes.wrong_answer;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class WrongAnswerInTest2Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class WrongAnswerInTest2 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String m = "Wrong answer in test #2";

    public WrongAnswerInTest2() {
        super(WrongAnswerInTest2Main.class);
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
        return new CheckResult(attach, "");
    }
}
