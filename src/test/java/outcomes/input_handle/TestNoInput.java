package outcomes.input_handle;

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

class TestNoInputMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();
        System.out.println(line1.length());
        System.out.println(line2.length());
    }
}

public class TestNoInput extends StageTest {
    public TestNoInput() {
        super(TestNoInputMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #2\n\n" +
            "Probably your program run out of input (Scanner tried to read more than expected).");
        exception.expectMessage(not(containsString("Fatal error")));
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .addInput("")
                .addInput(""),

            new TestCase()
                .addInput("")
                .addInput(out -> null)
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
