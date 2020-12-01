package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.dynamic.output.InfiniteLoopDetector;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.After;

class InfiniteLoopTestNotWorkingMain {
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Line");
        }
    }
}

public class InfiniteLoopTestNotWorking extends StageTest {

    boolean prevValue;

    public InfiniteLoopTestNotWorking() {
        prevValue = InfiniteLoopDetector.isWorking();
        InfiniteLoopDetector.setWorking(false);
    }

    @After
    public void after() {
        InfiniteLoopDetector.setWorking(prevValue);
    }

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram test = new TestedProgram(InfiniteLoopTestNotWorkingMain.class);
        test.start();
        return CheckResult.correct();
    }
}
