package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.PredefinedIOTestCase;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestPredefinedIO extends BaseStageTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(n);
        System.out.println(n);
    }

    public TestPredefinedIO() {
        super(TestPredefinedIO.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new PredefinedIOTestCase("123", "123\n123"),
            new PredefinedIOTestCase("567", "567\n567")
        );
    }
}
