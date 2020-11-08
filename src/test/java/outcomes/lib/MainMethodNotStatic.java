package outcomes.lib;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class MainMethodNotStaticMain {
    public void main(String[] args) {
        System.out.println("Hello World!");
    }
}

public class MainMethodNotStatic extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Main method is not static in class outcomes.lib.MainMethodNotStaticMain";

    public MainMethodNotStatic() {
        super(MainMethodNotStaticMain.class);
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
