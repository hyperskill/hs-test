package outcomes.dynamic_output;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestDynamicOutputWithLinesMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("123");
        if (!scanner.nextLine().equals("line1")) {
            throw new RuntimeException();
        }
        System.out.print("print:");
        if (!scanner.nextLine().equals("line2")
            || ! scanner.nextLine().equals("line3")) {
            throw new RuntimeException();
        }
    }
}


public class TestDynamicOutputWithLines extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n\n" +
        "Please find below the output of your program during this failed test.\n" +
        "Note that the '>' character indicates the beginning of the input line.\n" +
        "\n---\n\n" +
        "123\n" +
        "> line1\n" +
        "print:> line2\n" +
        "> line3";

    public TestDynamicOutputWithLines() {
        super(TestDynamicOutputWithLinesMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("line1\nline2\nline3")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.wrong("");
    }
}
