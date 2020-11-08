package outcomes.wrong_answer;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class WrongAnswerDynamicMethod1Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        scanner.nextLine();
    }
}

public class WrongAnswerDynamicMethod1 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #1",
        "WA TEST 1"
    };

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(WrongAnswerDynamicMethod1Main.class);
        pr.start();
        return CheckResult.wrong("WA TEST 1");
    }
}
