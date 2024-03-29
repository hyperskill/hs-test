package outcomes.separate_package.not_public_2;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class MainMethodNotPublic2Main {
    protected static void main(String[] args) {
        System.out.print("Hello World");
    }
}

public class MainMethodNotPublic2 extends UserErrorTest {

    @ContainsMessage
    String m =
            "Error in test #1\n" +
                    "\n" +
                    "Cannot find a main method in class " +
                    "\"outcomes.separate_package.not_public_2.MainMethodNotPublic2Main\".\n" +
                    "Check if you declared it as \"public static void main(String[] args)\".";

    public MainMethodNotPublic2() {
        super(MainMethodNotPublic2Main.class);
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
