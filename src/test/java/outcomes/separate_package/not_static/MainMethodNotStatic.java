package outcomes.separate_package.not_static;

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
        "Cannot find a class with a main method in package \"outcomes.separate_package.not_static\".\n" +
        "Check if you declared it as \"public static void main(String[] args)\".";

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
