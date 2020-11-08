package outcomes.test_passed_thrown;

import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestPassedThrownInDynamicInput2Main {
    public static void main(String[] args) {
        System.out.print("2");
        new Scanner(System.in).next();
    }
}

public class TestPassedThrownInDynamicInput2 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n\n" +
        "fail inside check";

    public TestPassedThrownInDynamicInput2() {
        super(TestPassedThrownInDynamicInput2Main.class);
    }

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>()
                .addInput(out -> {
                    if (out.equals("1")) {
                        throw new TestPassed();
                    }
                    return "2";
                }),

            new TestCase<Boolean>()
                .addInput(out -> {
                    if (out.equals("2")) {
                        throw new TestPassed();
                    }
                    return "1";
                })
        );
    }

    @Override
    public CheckResult check(String reply, Boolean attach) {
        return CheckResult.wrong("fail inside check");
    }
}
