package outcomes.lib;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class MainMethodNutPublic1 {
    private static void main(String[] args) {
        System.out.print("Hello World");
    }
}

public class MainMethodNotPublic1 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Main method is not public in class outcomes.lib.MainMethodNutPublic1";

    public MainMethodNotPublic1() {
        super(MainMethodNutPublic1.class);
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
