package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorRuntimeExitInGenerateMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorRuntimeExitInGenerate extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error during testing",
        "ProgramExited: Tried to exit"
    };

    public UnexpectedErrorRuntimeExitInGenerate() {
        super(UnexpectedErrorRuntimeExitInGenerateMain.class);
    }

    @Override
    public List<TestCase> generate() {
        Runtime.getRuntime().exit(0);
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
