package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestInputHandler4Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (123 != scanner.nextInt()
            || !"234".equals(scanner.next())
            || !"".equals(scanner.nextLine())
            || !" w345 456".equals(scanner.nextLine())
            || !"w567".equals(scanner.next())
            || 678 != scanner.nextInt()) {
            throw new RuntimeException();
        }
    }
}

public class TestInputHandler4 extends StageTest {
    public TestInputHandler4() {
        super(TestInputHandler4Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setInput("123 234\n w345 456\n w567 678 \n")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
