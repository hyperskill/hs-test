package outcomes.wrong_answer;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WrongAnswerDynamicTesting2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println(scanner.nextLine());
    }
}

public class WrongAnswerDynamicTesting2 extends UserErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #2",
        "WA TEST 2"
    };

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(WrongAnswerDynamicTesting2Main.class);
                String out = pr.start();
                if (!out.equals("1\n")) {
                    return CheckResult.wrong("WA TEST 1");
                }
                out += pr.execute("2");
                return new CheckResult(out.equals("1\n2\n"), "");
            }),

            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(WrongAnswerDynamicTesting2Main.class);
                String out = pr.start();
                if (!out.equals("2\n")) {
                    return CheckResult.wrong("WA TEST 2");
                }
                out += pr.execute("3");
                return new CheckResult(out.equals("2\n3\n"), "");
            })
        );
    }
}
