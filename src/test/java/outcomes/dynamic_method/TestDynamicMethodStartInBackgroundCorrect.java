package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestDynamicMethodStartInBackgroundCorrectMain {
    public static void main(String[] args) {
        System.out.println("Server started!");
        try {
            Thread.sleep(100);
            System.out.println("S1");
            Thread.sleep(100);
            System.out.println("S2");
            Thread.sleep(100);
            System.out.println("S3");
            Thread.sleep(100);
            System.out.println("S4");
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
            System.out.println("Server interrupted!");
        }
    }
}

public class TestDynamicMethodStartInBackgroundCorrect extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodStartInBackgroundCorrectMain.class);

        server.startInBackground();
        sleep(50);

        String out = server.getOutput();
        if (!out.equals("Server started!\n")) {
            return CheckResult.wrong("");
        }

        sleep(100);

        out = server.getOutput();
        if (!out.equals("S1\n")) {
            return CheckResult.wrong("");
        }

        sleep(200);

        out = server.getOutput();
        if (!out.equals("S2\nS3\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
