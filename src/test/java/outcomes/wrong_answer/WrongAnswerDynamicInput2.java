package outcomes.wrong_answer;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WrongAnswerDynamicInput2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println(scanner.nextLine());
    }
}

public class WrongAnswerDynamicInput2 extends UserErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #2",
        "WA TEST 2"
    };

    public WrongAnswerDynamicInput2() {
        super(WrongAnswerDynamicInput2Main.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(out -> {
                    if (out.equals("1\n"))  {
                        return "2";
                    }
                    return CheckResult.wrong("WA TEST 1");
                })
                .setAttach("1\n2\n"),

            new TestCase<String>()
                .addInput(out -> {
                    if (out.equals("2"))  {
                        return "3";
                    }
                    return CheckResult.wrong("WA TEST 2");
                })
                .setAttach("2\n3\n")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
