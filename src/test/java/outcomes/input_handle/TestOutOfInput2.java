package outcomes.input_handle;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestOutOfInput2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 6; i++) {
            System.out.println(scanner.nextInt());
        }
    }
}

public class TestOutOfInput2 extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",

        "Probably your program run out of input " +
        "(Scanner tried to read more than expected)",

        "java.util.NoSuchElementException"
    };

    public TestOutOfInput2() {
        super(TestOutOfInput2Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(o -> "1")
                .addInput(o -> "2")
                .addInput(o -> "3")
                .addInput(2, o -> "4")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }

}
