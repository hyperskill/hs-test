package outcomes.wrong_answer_thrown;

import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WrongAnswerThrownInDynamicInput2Main {
    public static void main(String[] args) {
        System.out.print("2");
        new Scanner(System.in).next();
    }
}

public class WrongAnswerThrownInDynamicInput2 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String m =
        "Wrong answer in test #2\n\n" +
        "Add input test 2";

    public WrongAnswerThrownInDynamicInput2() {
        super(WrongAnswerThrownInDynamicInput2Main.class);
    }

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>()
                .addInput(out -> {
                    if (out.equals("1")) {
                        throw new WrongAnswer("Add input test 1");
                    }
                    return "2";
                }),

            new TestCase<Boolean>()
                .addInput(out -> {
                    if (out.equals("2")) {
                        throw new WrongAnswer("Add input test 2");
                    }
                    return "1";
                })
        );
    }

    @Override
    public CheckResult check(String reply, Boolean attach) {
        return CheckResult.correct();
    }
}
