package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.common.Utils.sleep;
import static org.hyperskill.hstest.dynamic.input.KotlinInput.readLine;

class InfiniteLoopTestInputRequestMain {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Long Line Long Line Long Line");
            try {
                System.out.println(readLine());
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}

public class InfiniteLoopTestInputRequest extends UserErrorTest {

    @ContainsMessage
    String m =
        "Error in test #1\n" +
        "\n" +
        "Infinite loop detected.\n" +
        "Between the last 20 input requests the texts being printed are identical.";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram test = new TestedProgram(InfiniteLoopTestInputRequestMain.class);
        test.start();
        test.stopInput();
        sleep(50);
        return null;
    }
}
