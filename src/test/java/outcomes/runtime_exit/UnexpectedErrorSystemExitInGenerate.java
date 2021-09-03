package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.common.JavaUtils.isSystemExitAllowed;

class UnexpectedErrorSystemExitInGenerateMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorSystemExitInGenerate extends UnexpectedErrorTest {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(isSystemExitAllowed());
    }

    @ContainsMessage
    String[] m = {
        "Unexpected error during testing",
        "ExitException: Tried to exit"
    };

    public UnexpectedErrorSystemExitInGenerate() {
        super(UnexpectedErrorSystemExitInGenerateMain.class);
    }

    @Override
    public List<TestCase> generate() {
        System.exit(0);
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
