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

class WrongAnswerDynamicInput2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println(scanner.nextLine());
    }
}

public class WrongAnswerDynamicInput2 extends StageTest<String> {

    public WrongAnswerDynamicInput2() {
        super(WrongAnswerDynamicInput2Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #2");
        exception.expectMessage("WA TEST 2");
        exception.expectMessage(not(containsString("Unexpected error")));
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
