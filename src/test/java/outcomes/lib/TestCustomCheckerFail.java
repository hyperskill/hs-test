package outcomes.lib;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class TestCustomCheckerFailMain {
    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}

public class TestCustomCheckerFail extends UnexpectedErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #2",
        "Can't check result: override \"check\" method"
    };

    public TestCustomCheckerFail() {
        super(TestCustomCheckerFailMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addArguments("-in", "123", "-out", "234")
                .setAttach("4\n-in\n123\n-out\n234\n")
                .setCheckFunc(this::customCheck),

            new TestCase<String>()
                .addArguments("-in", "435")
                .addArguments("-out", "567", "789")
                .setAttach("5\n-in\n435\n-out\n567\n789\n")
        );
    }

    private CheckResult customCheck(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
