package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

public class TestCommandLineArgumentsFailedDynamicMethod4 extends StageTest<String> {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #1\n" +
                "\n" +
                "Please find below the output of your program during this failed test.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "Arguments for Main2: --second main\n" +
                "\n" +
                "0"
        );

        exception.expectMessage(not(containsString("Fatal error")));
    }

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(Main.class);
        pr.start();

        TestedProgram pr2 = new TestedProgram(Main2.class);
        pr2.start("--second", "main");

        return new CheckResult(false, "");
    }
}
