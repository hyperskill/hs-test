package outcomes;

import org.hyperskill.hstest.v4.stage.MainMethodTest;
import org.hyperskill.hstest.v4.testcase.CheckResult;
import org.hyperskill.hstest.v4.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputMismatch extends MainMethodTest {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(s.nextInt());
        System.out.println(s.nextLine());
        System.out.println(s.nextInt());
    }

    public InputMismatch() throws Exception {
        super(InputMismatch.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1");
        exception.expectMessage("Probably you have nextInt() " +
            "(or similar Scanner method) followed by nextLine() - " +
            "in this situation nextLine() often gives an empty string " +
            "and the second nextLine() gives correct string.");
    }

    @Override
    public List<TestCase> generateTestCases() {
        return Arrays.asList(
            new TestCase().setInput("234\nqwe\n345")
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.TRUE;
    }

}
