package outcomes.wrong_answer;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WrongAnswerDynamicInput1Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        scanner.nextLine();
    }
}

public class WrongAnswerDynamicInput1 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #1",
        "WA TEST 1"
    };

    public WrongAnswerDynamicInput1() {
        super(WrongAnswerDynamicInput1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .addInput(out -> CheckResult.wrong("WA TEST 1"))
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.wrong("");
    }
}
