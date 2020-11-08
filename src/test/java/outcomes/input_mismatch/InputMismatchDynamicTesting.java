package outcomes.input_mismatch;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class InputMismatchDynamicTestingMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println(s.nextInt());
        System.out.println(s.nextLine());
        System.out.println(s.nextInt());
    }
}

public class InputMismatchDynamicTesting extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",

        "Probably you have nextInt() " +
        "(or similar Scanner method) followed by nextLine() - " +
        "in this situation nextLine() often gives an " +
        "empty string and another one nextLine() call gives correct string."
    };

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(InputMismatchDynamicTestingMain.class);
                pr.start();
                pr.execute("234\nqwe\n345");
                return CheckResult.correct();
            })
        );
    }
}
