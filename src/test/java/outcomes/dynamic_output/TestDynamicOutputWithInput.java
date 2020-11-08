package outcomes.dynamic_output;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestDynamicOutputWithInputMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Print x and y: ");
        scanner.nextInt();
        scanner.nextInt();
        scanner.nextInt();
        System.out.println("Another num:");
        scanner.nextInt();
    }
}


public class TestDynamicOutputWithInput extends UserErrorTest<String> {

    @ContainsMessage
    String m1 = "Wrong answer in test #1";

    @ContainsMessage
    String m2 =
        "Please find below the output of your program during this failed test.\n" +
        "Note that the '>' character indicates the beginning of the input line.\n" +
        "\n---\n\n" +
        "Print x and y: > 123 456\n" +
        "> 678\n" +
        "Another num:\n" +
        "> 248";

    public TestDynamicOutputWithInput() {
        super(TestDynamicOutputWithInputMain.class);
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
