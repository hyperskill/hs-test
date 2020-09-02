package outcomes.runtime_exit;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorSystemExitInGenerateMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorSystemExitInGenerate extends StageTest {

    public UnexpectedErrorSystemExitInGenerate() {
        super(UnexpectedErrorSystemExitInGenerateMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Unexpected error during testing");
        exception.expectMessage("CheckExitCalled: Tried to exit");
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