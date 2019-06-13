package outcomes;

import org.hyperskill.hstest.v5.stage.BaseStageTest;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class TestCustomChecker extends BaseStageTest<String> {

    public static void main(String[] args) {
        System.out.println(args.length);
        for (String arg : args) {
            System.out.println(arg);
        }
    }

    public TestCustomChecker() {
        super(TestCustomChecker.class);
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
                .setCheckFunc(this::customCheck)
        );
    }

    private CheckResult customCheck(String reply, String clue) {
        return new CheckResult(reply.equals(clue));
    }
}
