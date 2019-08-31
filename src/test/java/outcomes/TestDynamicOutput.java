package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestDynamicOutput extends BaseStageTest<String> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println("2");
        String line3 = scanner.nextLine();
        if (!line3.equals("3")) {
            int x = 0/0;
        }
        System.out.println("5");
        String line4 = scanner.nextLine();
        if (!line4.equals("4")) {
            int x = 0/0;
        }
        System.out.println("6");
        String line7 = scanner.nextLine();
        if (!line7.equals("7")) {
            int x = 0/0;
        }
        String line8 = scanner.nextLine();
        if (!line8.equals("8")) {
            int x = 0/0;
        }
    }

    public TestDynamicOutput() {
        super(TestDynamicOutput.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(out -> {
                    if (!out.equals("1\n2\n")) {
                        int x = 0/0;
                    }
                    return "3\n4";
                })
                .addInput(out -> {
                    if (!out.equals("5\n6\n")) {
                        int x = 0/0;
                    }
                    return "7\n8\n";
                })
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.TRUE;
    }
}
