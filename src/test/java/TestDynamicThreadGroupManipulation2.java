import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestDynamicThreadGroupManipulation2Server {
    public static void main(String[] args) throws Exception {

        ThreadGroup tg = new ThreadGroup("123");

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

public class TestDynamicThreadGroupManipulation2 extends StageTest<String> {

    public TestDynamicThreadGroupManipulation2() {
        super(TestDynamicThreadGroupManipulation2Server.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setDynamicInput(() -> {
                TestedProgram server = new TestedProgram(
                    TestDynamicThreadGroupManipulation2Server.class);
                server.start();
                return CheckResult.correct();
            })
        );
    }
}
