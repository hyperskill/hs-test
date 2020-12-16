package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestDynamicMethodWrongAnswerInCheckMethodMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Server started!");
        System.out.println("S1: " + scanner.nextLine());
        System.out.println("S2: " + scanner.nextLine());
    }
}

public class TestDynamicMethodWrongAnswerInCheckMethod extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "WA1";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram main = new TestedProgram(
            TestDynamicMethodWrongAnswerInCheckMethodMain.class);

        main.start();
        main.execute("main");
        main.execute("main2");
        return null;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.wrong("WA1");
    }
}
