package outcomes.threads;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hyperskill.hstest.common.JavaUtils.isSecurityManagerAllowed;

class TestDynamicTestingThreadGroupManipulation1Server {
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

public class TestDynamicTestingThreadGroupManipulation1 extends UserErrorTest<String> {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(isSecurityManagerAllowed());
    }

    @ContainsMessage
    String m =
        "Exception in test #1\n" +
        "\n" +
        "java.security.AccessControlException: Cannot access or create ThreadGroup objects";

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram server = new TestedProgram(
                    TestDynamicTestingThreadGroupManipulation1Server.class);
                server.start();
                return CheckResult.correct();
            })
        );
    }
}
