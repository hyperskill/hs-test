package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SuccessButNotUsedInput2 extends BaseStageTest<String> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("HELLO");
        System.out.println(scanner.nextLine());
        System.out.println(scanner.nextLine());
    }

    public SuccessButNotUsedInput2() {
        super(SuccessButNotUsedInput2.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(out -> {
                    if (out.equals("HELLO\n")) {
                        return CheckResult.TRUE;
                    }
                    return "";
                })
                .setAttach("NO")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach));
    }
}
