package outcomes.input_handle;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestOutOfInput4Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 6; i++) {
            System.out.println(scanner.nextInt());
        }
    }
}

public class TestOutOfInput4 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Error in test #1\n" +
        "\n" +
        "Program ran out of input. You tried to read more, than expected."
    };

    public TestOutOfInput4() {
        super(TestOutOfInput4Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(2, o -> "1\n2")
                .addInput(o -> "2")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }

}
