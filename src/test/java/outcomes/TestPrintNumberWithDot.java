package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class TestPrintNumberWithDotMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double d = 1.234;
        float f = 1.234f;
        switch (scanner.nextInt()) {
            case 1:
                System.out.print(d);
                break;
            case 2:
                System.out.print(f);
                break;
            case 3:
                System.out.printf("%f", d);
                break;
            case 4:
                System.out.printf("%f", f);
                break;
            case 5:
                System.out.format("%f", d);
                break;
            case 6:
                System.out.format("%f", f);
                break;
        }
    }
}

public class TestPrintNumberWithDot extends StageTest<String> {

    public TestPrintNumberWithDot() {
        super(TestPrintNumberWithDotMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("1"),
            new TestCase<String>().setInput("2"),
            new TestCase<String>().setInput("3"),
            new TestCase<String>().setInput("4"),
            new TestCase<String>().setInput("5"),
            new TestCase<String>().setInput("6")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.startsWith("1.234"), "");
    }
}
