package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class TestCaseSameObjectsMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello");
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
    }
}

public class TestCaseSameObjects extends StageTest<String> {

    public TestCaseSameObjects() {
        super(TestCaseSameObjectsMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        TestCase<String> test = new TestCase<String>()
            .addInput(3, out -> out)
            .setAttach("Hello\nHello\nHello\nHello\n");

        return Arrays.asList(
            test, test, test, test, test
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
