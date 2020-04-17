package outcomes.thread_group;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Scanner;

class TestDoPrivilegedAccessThreadGroupMain {
    public static void main(String[] args) {
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            ThreadGroup currentThreadGroup =
                Thread.currentThread().getThreadGroup();
            ThreadGroup parentThreadGroup = currentThreadGroup.getParent();
            while (parentThreadGroup != null) {
                currentThreadGroup = parentThreadGroup;
                parentThreadGroup = currentThreadGroup.getParent();
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("S1: " + scanner.nextLine());
            System.out.println("S2: " + scanner.nextLine());

            return null;
        });
    }
}

public class TestDoPrivilegedAccessThreadGroup extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDoPrivilegedAccessThreadGroupMain.class);
        String start = server.start();
        if (!start.equals("Server started!\n")) {
            return CheckResult.wrong("");
        }
        if (!server.execute("123").equals("S1: 123\n")) {
            return CheckResult.wrong("");
        }
        return CheckResult.correct();
    }
}
