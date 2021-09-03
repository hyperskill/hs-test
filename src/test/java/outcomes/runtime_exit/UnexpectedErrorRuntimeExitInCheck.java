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

class UnexpectedErrorRuntimeExitInCheckMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorRuntimeExitInCheck extends UnexpectedErrorTest {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(isSystemExitAllowed());
    }

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "ExitException: Tried to exit"
    };

    public UnexpectedErrorRuntimeExitInCheck() {
        super(UnexpectedErrorRuntimeExitInCheckMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        Runtime.getRuntime().exit(0);
        return CheckResult.correct();
    }
}
