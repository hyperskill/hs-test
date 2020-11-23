package outcomes.number_format_exception;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class NumberFormatExceptionInCheck4Main {
    public static void main(String[] args) {
        System.out.println("qwe");
    }
}


public class NumberFormatExceptionInCheck4 extends StageTest {

    public NumberFormatExceptionInCheck4() {
        super(NumberFormatExceptionInCheck4Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
        "Error in test #1\n" +
                "\n" +
                "Cannot parse Double from the output part \"qwe\""
        );

        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(new TestCase());
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        Double.parseDouble(reply);
        return CheckResult.correct();
    }
}
