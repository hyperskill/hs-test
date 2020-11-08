package outcomes.wrong_answer_thrown;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class WrongAnswerThrownInDynamicMethod1Main {
    public static void main(String[] args) {
        System.out.print("1");
        new Scanner(System.in).next();
    }
}

public class WrongAnswerThrownInDynamicMethod1 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #",
        "\n\nAdd input test 1"
    };

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(
            WrongAnswerThrownInDynamicMethod1Main.class);
        String out = pr.start();
        if (out.equals("1")) {
            throw new WrongAnswer("Add input test 1");
        }
        pr.execute("2");
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(
            WrongAnswerThrownInDynamicMethod1Main.class);
        String out = pr.start();
        if (out.equals("2")) {
            throw new WrongAnswer("Add input test 2");
        }
        pr.execute("1");
        return CheckResult.correct();
    }
}
