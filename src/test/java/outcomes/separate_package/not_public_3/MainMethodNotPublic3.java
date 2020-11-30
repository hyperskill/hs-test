package outcomes.separate_package.not_public_3;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class MainMethodNotPublic3Main {
    static void main(String[] args) {
        System.out.print("Hello World");
    }
}

public class MainMethodNotPublic3 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Cannot find a class with a main method.\n" +
        "Check if you declared it as \"public static void main(String[] args)\".";

    public MainMethodNotPublic3() {
        super(MainMethodNotPublic3Main.class);
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
