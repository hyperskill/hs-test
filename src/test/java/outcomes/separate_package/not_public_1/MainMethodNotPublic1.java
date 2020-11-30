package outcomes.separate_package.not_public_1;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class MainMethodNotPublic1Main {
    private static void main(String[] args) {
        System.out.print("Hello World");
    }
}

public class MainMethodNotPublic1 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot find a class with a main method.\n" +
        "Check if you declared it as \"public static void main(String[] args)\".";

    public MainMethodNotPublic1() {
        super(MainMethodNotPublic1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("Hello World"), "");
    }
}
