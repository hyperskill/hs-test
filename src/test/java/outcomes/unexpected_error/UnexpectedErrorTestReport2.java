package outcomes.unexpected_error;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.ExecutionOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class UnexpectedErrorTestReport2 extends StageTest {

    boolean oldInsideDockerFlag;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        oldInsideDockerFlag = ExecutionOptions.insideDocker;
        ExecutionOptions.insideDocker = true;

        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Unexpected error during testing\n"
                + "\n"
                + "We have recorded this bug and will fix it soon.\n"
                + "\n"
                + "Submitted via web\n"
                + "\n");
    }

    @After
    public void after() {
        ExecutionOptions.insideDocker = oldInsideDockerFlag;
    }
}
