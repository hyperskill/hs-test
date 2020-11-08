package outcomes.input_mismatch;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class InputMismatchMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(s.nextInt());
        System.out.println(s.nextLine());
        System.out.println(s.nextInt());
    }
}

public class InputMismatch extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",

        "Probably you have nextInt() " +
        "(or similar Scanner method) followed by nextLine() - " +
        "in this situation nextLine() often gives an " +
        "empty string and another one nextLine() call gives correct string."
    };

    public InputMismatch() {
        super(InputMismatchMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setInput("234\nqwe\n345")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }

}
