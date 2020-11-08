package outcomes.wrong_answer;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class WrongAnswerDynamicMethod2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println(scanner.nextLine());
    }
}

public class WrongAnswerDynamicMethod2 extends UserErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #",
        "WA TEST 2"
    };

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(WrongAnswerDynamicMethod2Main.class);
        String out = pr.start();
        if (!out.equals("1\n")) {
            return CheckResult.wrong("WA TEST 1");
        }
        out += pr.execute("2");
        return new CheckResult(out.equals("1\n2\n"), "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(WrongAnswerDynamicMethod2Main.class);
        String out = pr.start();
        if (!out.equals("2\n")) {
            return CheckResult.wrong("WA TEST 2");
        }
        out += pr.execute("3");
        return new CheckResult(out.equals("2\n3\n"), "");
    }
}
