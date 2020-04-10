package outcomes.input_handle;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestInputHandler3Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!"123".equals(scanner.next())
            || !"234".equals(scanner.next())
            || !"w345".equals(scanner.next())
            || !"456".equals(scanner.next())
            || !"w567".equals(scanner.next())
            || !"678e".equals(scanner.next())) {
            throw new RuntimeException();
        }
    }
}

public class TestInputHandler3 extends StageTest {
    public TestInputHandler3() {
        super(TestInputHandler3Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setInput("123 234\n w345 456\n w567 678e \n")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
