package outcomes.lib;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class MainMethodNutPublic2 {
    protected static void main(String[] args) {
        System.out.print("Hello World");
    }
}

public class MainMethodNotPublic2 extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Main method is not public in class outcomes.lib.MainMethodNutPublic2";

    public MainMethodNotPublic2() {
        super(MainMethodNutPublic2.class);
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
