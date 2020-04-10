package outcomes;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestInputHandler1Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (123 != scanner.nextInt()
            || 234 != scanner.nextInt()
            || 345 != scanner.nextInt()
            || 456 != scanner.nextInt()
            || 567 != scanner.nextInt()
            || 678 != scanner.nextInt()) {
            throw new RuntimeException();
        }
    }
}

public class TestInputHandler1 extends StageTest {
    public TestInputHandler1() {
        super(TestInputHandler1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setInput("123 234 345\n456 567 678")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
