package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestNotRunNotNeededInputFuncs extends BaseStageTest<String> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println("2");
        String line3 = scanner.nextLine();
        System.out.println("5");
        String line4 = scanner.nextLine();
        System.out.println("6");
    }

    public TestNotRunNotNeededInputFuncs() {
        super(TestNotRunNotNeededInputFuncs.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(out -> {
                    return "3\n4";
                })
                .addInput(out -> {
                    int x = 0/0;
                    return "3\n4";
                })
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.TRUE;
    }
}

