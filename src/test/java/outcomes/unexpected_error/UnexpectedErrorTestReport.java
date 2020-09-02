package outcomes.unexpected_error;

import org.hyperskill.hstest.stage.StageTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class UnexpectedErrorTestReport extends StageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
                "Unexpected error during testing\n"
                + "\n"
                + "We have recorded this bug and will fix it soon.\n"
                + "\n"
                + "Submitted via IDE\n"
                + "\n"
                + "OS ");
    }
}
