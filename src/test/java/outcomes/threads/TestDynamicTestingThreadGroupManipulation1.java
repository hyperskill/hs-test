package outcomes.threads;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

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

public class TestDynamicTestingThreadGroupManipulation1 extends StageTest<String> {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Exception in test #1\n" +
                "\n" +
                "java.security.AccessControlException: Cannot access or create ThreadGroup objects"
        );

        exception.expectMessage(not(containsString("Fatal error")));
        exception.expectMessage(not(containsString("at org.hyperskill.hstest")));
        exception.expectMessage(not(containsString("org.junit.")));
        exception.expectMessage(not(containsString("at sun.reflect.")));
        exception.expectMessage(not(containsString("at java.base/jdk.internal.reflect.")));
    }

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
