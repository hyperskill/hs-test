package outcomes.wrong_answer_thrown;

import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class WrongAnswerThrownInCheck1Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class WrongAnswerThrownInCheck1 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String m =
        "Wrong answer in test #2\n\n" +
        "Wrong answer from check attach false";

    public WrongAnswerThrownInCheck1() {
        super(WrongAnswerThrownInCheck1Main.class);
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
            throw new WrongAnswer("Wrong answer from check attach false");
        }
        return new CheckResult(true, "");
    }
}
