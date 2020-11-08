package outcomes.dynamic_output;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class TestDynamicOutputWithoutInputMain {
    public static void main(String[] args) {
        System.out.print("Print x and y: ");
        System.out.println("123 456");
        System.out.println("Another num:");
    }
}

public class TestDynamicOutputWithoutInput extends UserErrorTest<String> {

    @ContainsMessage
    String m = "Wrong answer in test #1";

    @ContainsMessage
    String m2 =
        "Please find below the output of your program during this failed test.\n" +
        "\n---\n\n" +
        "Print x and y: 123 456\n" +
        "Another num:";

    public TestDynamicOutputWithoutInput() {
        super(TestDynamicOutputWithoutInputMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("123 456\n678\n248")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.wrong("");
    }
}
