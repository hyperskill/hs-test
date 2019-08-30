package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class TestOutOfInput2 extends BaseStageTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 6; i++) {
            System.out.println(scanner.nextInt());
        }
    }

    public TestOutOfInput2() {
        super(TestOutOfInput2.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1");
        exception.expectMessage("Maybe you created more than one instance of Scanner?");
        exception.expectMessage("java.util.NoSuchElementException");
        exception.expectMessage(not(containsString("Fatal error")));
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(o -> "1")
                .addInput(o -> "2")
                .addInput(o -> "3")
                .addInput(2, o -> "4")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.TRUE;
    }

}
