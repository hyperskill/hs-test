package outcomes.scanner;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestStaticScannerHasNextMain {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (scanner.hasNextInt()) {
            System.out.println(scanner.nextInt());
        }
    }
}

public class TestStaticScannerHasNext extends StageTest<String> {

    public TestStaticScannerHasNext() {
        super(TestStaticScannerHasNextMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("123 435").setAttach("123\n435\n"),
            new TestCase<String>().setInput("1234 546").setAttach("1234\n546\n")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
