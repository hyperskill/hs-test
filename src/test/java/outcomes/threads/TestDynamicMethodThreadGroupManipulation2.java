package outcomes.threads;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestDynamicMethodThreadGroupManipulation2Server {
    public static void main(String[] args) throws Exception {

        ThreadGroup tg = new ThreadGroup("123");

        Thread t = new Thread(tg, () -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Server started!");
            System.out.println("S1: " + scanner.nextLine());
            System.out.println("S2: " + scanner.nextLine());
        });

        t.start();
        // TODO without while+try it fails
        while (t.isAlive()) {
            try {
                t.join();
            } catch (InterruptedException ignored) { }
        }
    }
}

public class TestDynamicMethodThreadGroupManipulation2 extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodThreadGroupManipulation2Server.class);
        server.start();
        return CheckResult.correct();
    }
}
