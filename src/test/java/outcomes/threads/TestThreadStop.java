package outcomes.threads;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Ignore;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestThreadStopMain {
    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (Exception ex) { }
        }
    }
}

@Ignore("Thread.stop() is not used to kill user threads")
public class TestThreadStop extends StageTest {
    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestThreadStopMain.class);
        pr.startInBackground();
        sleep(100);
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestThreadStopMain.class);
        pr.startInBackground();
        sleep(100);
        return CheckResult.correct();
    }
}
