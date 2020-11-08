package outcomes.wrong_answer;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WrongAnswerDynamicInput4Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
    }
}

public class WrongAnswerDynamicInput4 extends UserErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #4",
        "WA TEST 4"
    };

    public WrongAnswerDynamicInput4() {
        super(WrongAnswerDynamicInput4Main.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput("1")
                .addInput(2, out -> {
                    if (out.equals(""))  {
                        return CheckResult.correct();
                    }
                    return CheckResult.correct();
                })
                .setAttach("1\n2\n2\n"),

            new TestCase<String>()
                .addInput(2, "3")
                .addInput("3")
                .addInput(out-> CheckResult.wrong("WA TEST 2"))
                .setAttach("3\n3\n3\n"),

            new TestCase<String>()
                .addInfInput("4")
                .addInput(out -> CheckResult.wrong("WA TEST 3"))
                .setAttach("4\n4\n4\n"),

            new TestCase<String>()
                .addInput(2, "5")
                .addInput(out -> {
                    if (out.equals("5\n")) {
                        return CheckResult.wrong("WA TEST 4");
                    }
                    return 5;
                })
                .setAttach("4\n4\n4\n")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
