package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestCaseSameObjects extends BaseStageTest<String> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello");
        System.out.println(scanner.nextLine());
    }

    public TestCaseSameObjects() {
        super(TestCaseSameObjects.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        TestCase<String> test = new TestCase<String>()
            .addInput(out -> out)
            .setAttach("Hello\nHello\n");

        return Arrays.asList(
            test, test, test, test, test
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach));
    }
}
