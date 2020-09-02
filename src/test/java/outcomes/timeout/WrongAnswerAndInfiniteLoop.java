package outcomes.timeout;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class WrongAnswerAndInfiniteLoopMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            s.nextLine();
        }
    }
}

public class WrongAnswerAndInfiniteLoop extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #1\n" +
                "\n" +
                "Test tried all the possible numbers but didn't guess the number\n" +
                "\n" +
                "Please find below the output of your program during this failed test.\n" +
                "Note that the '>' character indicates the beginning of the input line.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "> 0\n" +
                "> 1\n" +
                "> 2\n" +
                "> 3\n" +
                "> 4\n" +
                "> 5\n" +
                "> 6\n" +
                "> 7\n" +
                "> 8\n" +
                "> 9"
        );

        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            WrongAnswerAndInfiniteLoopMain.class);

        main.start();

        int tries = 0;
        while (true) {
            String output = main.execute("" + tries);
            if (output.contains("you guessed")) {
                break;
            }
            tries++;

            if (tries == 10) {
                return CheckResult.wrong(
                    "Test tried all the possible numbers but " +
                        "didn't guess the number");
            }
        }

        return CheckResult.correct();
    }
}
