package outcomes.fatal_error;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.ExecutionOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class FatalErrorTestReport2 extends StageTest {

    boolean oldInsideDockerFlag;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        oldInsideDockerFlag = ExecutionOptions.insideDocker;
        ExecutionOptions.insideDocker = true;

        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Fatal error during testing, please send the report to support@hyperskill.org\n"
                + "\n"
                + "Submitted via web\n"
                + "\n");
    }

    @After
    public void after() {
        ExecutionOptions.insideDocker = oldInsideDockerFlag;
    }
}
