package outcomes.dynamic_method;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.common.Utils.sleep;

class TestDynamicMethodStartInBackgroundWrongAnswerMain {
    public static void main(String[] args) {
        System.out.println("Server started!");
        try {
            Thread.sleep(100);
            System.out.println("S1");
            Thread.sleep(100);
            System.out.println("S2");
            Thread.sleep(100);
            System.out.println("S3");
            Thread.sleep(100000);
            System.out.println("S4");
        } catch (InterruptedException ex) {
            System.out.println(ex);
            System.out.println("Server interrupted!");
        }
    }
}

public class TestDynamicMethodStartInBackgroundWrongAnswer extends UserErrorTest<String> {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "Server started!\n" +
        "S1";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram server = new TestedProgram(
            TestDynamicMethodStartInBackgroundWrongAnswerMain.class);

        server.startInBackground();
        sleep(150);
        return CheckResult.wrong("");
    }
}
