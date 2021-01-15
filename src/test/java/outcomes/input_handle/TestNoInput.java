package outcomes.input_handle;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestNoInputMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();
        System.out.println(line1.length());
        System.out.println(line2.length());
    }
}

public class TestNoInput extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #2\n" +
        "\n" +
        "Program ran out of input. You tried to read more, than expected.";

    public TestNoInput() {
        super(TestNoInputMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .addInput("")
                .addInput(""),

            new TestCase()
                .addInput("")
                .addInput(out -> null)
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
