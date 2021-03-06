package outcomes.scanner;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestStaticInitScanner2Main {
    private static Scanner scanner;
    private static int int1;
    private static int int2;

    static {
        scanner = new Scanner(System.in);
        int1 = scanner.nextInt();
        int2 = scanner.nextInt();
    }

    public static void main(String[] args) {
        System.out.println(int1);
        System.out.println(int2);
    }
}

public class TestStaticInitScanner2 extends StageTest<String> {

    public TestStaticInitScanner2() {
        super(TestStaticInitScanner2Main.class);
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
