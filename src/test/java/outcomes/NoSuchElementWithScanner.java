package outcomes;

import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NoSuchElementWithScanner extends BaseStageTest {

    public static void main(String[] args) {
        new Scanner(System.in).nextInt();
    }

    public NoSuchElementWithScanner() {
        super(NoSuchElementWithScanner.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1");
        exception.expectMessage("you created more than one Scanner object " +
            "(it is preferred to use a single Scanner in program)");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.FALSE;
    }
}
