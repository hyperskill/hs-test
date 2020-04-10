package outcomes.simple_test;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.SimpleTestCase;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestSimpleTestCaseMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(n);
        System.out.println(n);
    }
}

public class TestSimpleTestCase extends StageTest {

    public TestSimpleTestCase() {
        super(TestSimpleTestCaseMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new SimpleTestCase("123", "123\n123"),
            new SimpleTestCase("567", "567\n567")
        );
    }
}
