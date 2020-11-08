package outcomes.wrong_answer_thrown;

import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WrongAnswerThrownInDynamicTesting1Main {
    public static void main(String[] args) {
        System.out.print("1");
        new Scanner(System.in).next();
    }
}

public class WrongAnswerThrownInDynamicTesting1 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n\n" +
        "Add input test 1";

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(WrongAnswerThrownInDynamicTesting1Main.class);
                String out = pr.start();
                if (out.equals("1")) {
                    throw new WrongAnswer("Add input test 1");
                }
                pr.execute("2");
                return CheckResult.correct();
            }),

            new TestCase<Boolean>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(WrongAnswerThrownInDynamicTesting1Main.class);
                String out = pr.start();
                if (out.equals("2")) {
                    throw new WrongAnswer("Add input test 2");
                }
                pr.execute("1");
                return CheckResult.correct();
            })
        );
    }
}
