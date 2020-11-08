package outcomes.unexpected_error;

import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

public class UnexpectedErrorTestReport extends UnexpectedErrorTest {
    @ContainsMessage
    String m =
        "Unexpected error during testing\n" +
        "\n" +
        "We have recorded this bug and will fix it soon.\n" +
        "\n" +
        "Submitted via IDE\n" +
        "\n" +
        "OS ";
}
