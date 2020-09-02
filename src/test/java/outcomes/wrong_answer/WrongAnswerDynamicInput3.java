package outcomes.wrong_answer;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class WrongAnswerDynamicInput3Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
    }
}

public class WrongAnswerDynamicInput3 extends StageTest<String> {

    public WrongAnswerDynamicInput3() {
        super(WrongAnswerDynamicInput3Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #3");
        exception.expectMessage("WA TEST 3");
        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInfInput(out -> {
                    if (out.equals(""))  {
                        return CheckResult.correct();
                    }
                    return CheckResult.correct();
                })
                .setAttach("1\n2\n2\n"),

            new TestCase<String>()
                .addInput(out -> {
                    if (out.equals(""))  {
                        return CheckResult.correct();
                    }
                    return CheckResult.wrong("WA TEST 2");
                })
                .addInput(2, out-> {
                    if (out.equals("3\n"))  {
                        return CheckResult.correct();
                    }
                    return CheckResult.wrong("WA TEST 2");
                })
                .setAttach("3\n3\n3\n"),

            new TestCase<String>()
                .addInput(out -> {
                    if (out.equals(""))  {
                        return "3";
                    }
                    return CheckResult.wrong("WA TEST 3");
                })
                .addInfInput(out-> {
                    if (out.equals("3\n"))  {
                        return "4";
                    }
                    return CheckResult.wrong("WA TEST 3");
                })
                .setAttach("3\n3\n4\n")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
