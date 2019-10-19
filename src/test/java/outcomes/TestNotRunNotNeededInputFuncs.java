package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


class TestNotRunNotNeededInputFuncsMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println("2");
        String line3 = scanner.nextLine();
        System.out.println("5");
        String line4 = scanner.nextLine();
        System.out.println("6");
    }
}

public class TestNotRunNotNeededInputFuncs extends BaseStageTest<String> {

    public TestNotRunNotNeededInputFuncs() {
        super(TestNotRunNotNeededInputFuncsMain.class);
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

