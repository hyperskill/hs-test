package outcomes.simple_test;

import org.hyperskill.hstest.testcase.SimpleTestCase;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestSimpleTestCaseFailMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(n);
    }
}

public class TestSimpleTestCaseFail extends UserErrorTest {

    @ContainsMessage
    String m = "Wrong answer in test #1";

    public TestSimpleTestCaseFail() {
        super(TestSimpleTestCaseFailMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new SimpleTestCase("123", "123\n123")
                .setFeedback("You should output a number twice"),

            new SimpleTestCase("567", "567\n567")
                .setFeedback("You should output this number twice")
        );
    }
}
