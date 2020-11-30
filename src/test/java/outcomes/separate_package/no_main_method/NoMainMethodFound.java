package outcomes.separate_package.no_main_method;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class NoMainMethodFoundMain {

}

public class NoMainMethodFound extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot find a class with a main method.\n" +
        "Check if you declared it as \"public static void main(String[] args)\".";

    public NoMainMethodFound() {
        super(NoMainMethodFoundMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }

}
