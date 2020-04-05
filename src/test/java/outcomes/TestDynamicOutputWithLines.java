package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

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


public class TestDynamicOutputWithLines extends StageTest<String> {

    public TestDynamicOutputWithLines() {
        super(TestDynamicOutputWithLinesMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #1\n\n" +
            "Please find below the output of your program during this failed test.\n" +
            "Note that the '>' character indicates the beginning of the input line.\n" +
            "\n---\n\n" +
            "123\n" +
            "> line1\n" +
            "print:> line2\n" +
            "> line3"
        );
        exception.expectMessage(not(containsString("Fatal error")));
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
