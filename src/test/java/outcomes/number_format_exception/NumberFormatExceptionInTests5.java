package outcomes.number_format_exception;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class NumberFormatExceptionInTests5Main {
    public static void main(String[] args) {
        System.out.println("qwe");
    }
}


public class NumberFormatExceptionInTests5 extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
        "Error in test #1\n" +
                "\n" +
                "Cannot parse Short from the output part \"qwe\""
        );

        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(NumberFormatExceptionInTests5Main.class);
        short number = Short.parseShort(main.start());
        return CheckResult.correct();
    }
}