package outcomes.command_line_args;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Collections;
import java.util.List;

class TestCommandLineArgumentsFailedWithSpacesMain {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

public class TestCommandLineArgumentsFailedWithSpaces extends UserErrorTest<String> {

    @ContainsMessage
    String s = "Arguments: -spaces \"some argument with spaces\" -number 234 -onlySpaces \"      \"";

    @Override
    public List<TestCase<String>> generate() {
        return Collections.singletonList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(
                    TestCommandLineArgumentsFailedWithSpacesMain.class);
                String out = pr.start(
                    "-spaces", "some argument with spaces",
                    "-number", "234", "-onlySpaces", "      ");
                return CheckResult.wrong("See arguments below:");
            })
        );
    }
}
