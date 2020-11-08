package outcomes.command_line_args;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

public class TestCommandLineArgumentsFailedDynamicMethod4 extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Arguments for Main2: --second main\n" +
        "\n" +
        "0";

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(Main.class);
        pr.start();

        TestedProgram pr2 = new TestedProgram(Main2.class);
        pr2.start("--second", "main");

        return new CheckResult(false, "");
    }
}
