package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.io.PrintWriter;
import java.util.ArrayList;
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
            case 7:
                PrintWriter printWriter1 = new PrintWriter(System.out);
                printWriter1.printf("%f", d);
                printWriter1.flush();
                break;
            case 8:
                PrintWriter printWriter2 = new PrintWriter(System.out);
                printWriter2.printf("%f", f);
                printWriter2.flush();
                break;
            case 9:
                System.out.println(Double.parseDouble("1.234"));
                break;
            case 10:
                System.out.println(Float.parseFloat("1.234"));
                break;
            case 11:
                Scanner scanner1 = new Scanner("1.234");
                System.out.println(scanner1.nextDouble());
                break;
            case 12:
                Scanner scanner2 = new Scanner("1.234");
                System.out.println(scanner2.nextFloat());
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
        List<TestCase<String>> tests = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            tests.add(new TestCase<String>().setInput("" + i));
        }
        return tests;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.startsWith("1.234"), "");
    }
}
