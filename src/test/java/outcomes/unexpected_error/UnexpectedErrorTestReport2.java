package outcomes.unexpected_error;

import org.hyperskill.hstest.testing.ExecutionOptions;
import org.junit.After;
import org.junit.Before;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorTestReport2 extends UnexpectedErrorTest {

    @ContainsMessage
    String m =
        "Unexpected error during testing\n" +
        "\n" +
        "We have recorded this bug and will fix it soon.\n" +
        "\n" +
        "Submitted via web\n" +
        "\n";

    boolean oldInsideDockerFlag;

    @Before
    public void before() {
        oldInsideDockerFlag = ExecutionOptions.insideDocker;
        ExecutionOptions.insideDocker = true;
    }

    @After
    public void after() {
        ExecutionOptions.insideDocker = oldInsideDockerFlag;
    }
}
