package outcomes.wrong_answer;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WrongAnswerDynamicTesting1Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        scanner.nextLine();
    }
}

public class WrongAnswerDynamicTesting1 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #1",
        "WA TEST 1"
    };

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(WrongAnswerDynamicTesting1Main.class);
                pr.start();
                return CheckResult.wrong("WA TEST 1");
            })
        );
    }
}
