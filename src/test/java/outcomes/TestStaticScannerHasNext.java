package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class UserClass3 {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (scanner.hasNextInt()) {
            System.out.println(scanner.nextInt());
        }
    }
}

public class TestStaticScannerHasNext extends BaseStageTest<String> {

    public TestStaticScannerHasNext() {
        super(UserClass3.class);
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
        return new CheckResult(reply.equals(attach));
    }
}
