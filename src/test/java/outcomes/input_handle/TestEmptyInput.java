package outcomes.input_handle;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestEmptyInputMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine();
        String line2 = scanner.nextLine();
        System.out.println(line1.length());
        System.out.println(line2.length());
    }
}

public class TestEmptyInput extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #1",
        "> \n> \n0\n0"
    };

    public TestEmptyInput() {
        super(TestEmptyInputMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .addInput("")
                .addInput("")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.wrong("");
    }
}
