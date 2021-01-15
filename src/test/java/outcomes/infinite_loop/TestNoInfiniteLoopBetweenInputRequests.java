package outcomes.infinite_loop;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class TestNoInfiniteLoopBetweenInputRequestsMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (!s.nextLine().startsWith("0")) {
            System.out.println(
                "\nChoose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "0) Exit");
        }
    }
}

public class TestNoInfiniteLoopBetweenInputRequests extends StageTest {

    public TestNoInfiniteLoopBetweenInputRequests() {
        super(TestNoInfiniteLoopBetweenInputRequestsMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Collections.singletonList(
            new TestCase<String>()
                .setInput("1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n0")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
