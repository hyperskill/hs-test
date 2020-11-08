package outcomes.threads;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Scanner;

class TestDynamicMethodThreadGroupManipulation1Server {
    public static void main(String[] args) throws Exception {

        ThreadGroup tg = Thread.currentThread().getThreadGroup().getParent();

        Thread t = new Thread(tg, () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("S1: " + scanner.nextLine());
            System.out.println("S2: " + scanner.nextLine());
        });

        t.start();
        t.join();
    }
}

public class TestDynamicMethodThreadGroupManipulation1 extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Exception in test #1\n" +
        "\n" +
        "java.security.AccessControlException: Cannot access or create ThreadGroup objects";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodThreadGroupManipulation1Server.class);
        server.start();
        return CheckResult.correct();
    }
}
