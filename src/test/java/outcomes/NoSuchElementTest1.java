package outcomes;

import org.hyperskill.hstest.v3.stage.MainMethodTest;
import org.hyperskill.hstest.v3.testcase.CheckResult;
import org.hyperskill.hstest.v3.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NoSuchElementTest1 extends MainMethodTest {

    public static void main(String[] args) {
        new Scanner(System.in).nextInt();
    }

    public NoSuchElementTest1() throws Exception {
        super(NoSuchElementTest1.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1\n\n" +
            "Maybe you created more than one instance of Scanner? " +
            "You should use a single Scanner in program.\n\n" +
            "java.util.NoSuchElementException");
    }

    @Override
    public List<TestCase> generateTestCases() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object clue) {
        return CheckResult.FALSE;
    }
}
