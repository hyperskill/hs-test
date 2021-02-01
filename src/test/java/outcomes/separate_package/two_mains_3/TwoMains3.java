package outcomes.separate_package.two_mains_3;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class Main1 {
    public static void main(String[] args) {
        System.out.print("Main1");
    }
}

class Main2 {
    public static void main(String[] args) {
        System.out.print("Main2");
    }
}

public class TwoMains3 extends UserErrorTest {

    @ContainsMessage
    String s =
        "Error in test #1\n" +
        "\n" +
        "There are 2 classes with main method in package \"outcomes.separate_package.two_mains_3\": " +
            "outcomes.separate_package.two_mains_3.Main1, " +
            "outcomes.separate_package.two_mains_3.Main2.\n" +
        "Leave only one of them to be executed.";

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("Main2"), "");
    }

}
