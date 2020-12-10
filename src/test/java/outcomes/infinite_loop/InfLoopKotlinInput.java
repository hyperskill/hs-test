package outcomes.infinite_loop;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.IOException;

import static org.hyperskill.hstest.dynamic.input.KotlinInput.readLine;

class InfLoopKotlinInputMain {
    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println(readLine());
        }
    }
}

public class InfLoopKotlinInput extends StageTest {
    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(InfLoopKotlinInputMain.class);
        pr.start();
        return CheckResult.correct();
    }
}
