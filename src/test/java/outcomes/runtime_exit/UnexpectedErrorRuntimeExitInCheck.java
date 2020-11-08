package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorRuntimeExitInCheckMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorRuntimeExitInCheck extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "ProgramExited: Tried to exit"
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
