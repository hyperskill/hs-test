package outcomes.test_passed_thrown;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestPassedThrownInDynamicMethod1Main {
    public static void main(String[] args) {
        System.out.print("1");
        new Scanner(System.in).next();
    }
}

public class TestPassedThrownInDynamicMethod1 extends UserErrorTest<Boolean> {

    @ContainsMessage
    String[] m = {
        "Wrong answer in test #",
        "\n\nfail inside check test2"
    };

    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicMethod1Main.class);
        String out = pr.start();
        if (out.equals("1")) {
            throw new TestPassed();
        }
        pr.execute("2");
        return CheckResult.wrong("fail inside check test1");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestPassedThrownInDynamicMethod1Main.class);
        String out = pr.start();
        if (out.equals("2")) {
            throw new TestPassed();
        }
        pr.execute("1");
        return CheckResult.wrong("fail inside check test2");
    }
}
