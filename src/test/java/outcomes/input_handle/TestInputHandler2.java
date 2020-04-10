package outcomes.input_handle;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestInputHandler2Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!"123 234".equals(scanner.nextLine())
            || !" 345 456".equals(scanner.nextLine())
            || !" 567 678 ".equals(scanner.nextLine())) {
            throw new RuntimeException();
        }
    }
}

public class TestInputHandler2 extends StageTest {
    public TestInputHandler2() {
        super(TestInputHandler2Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setInput("123 234\n 345 456\n 567 678 \n")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
