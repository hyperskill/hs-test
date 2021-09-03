package outcomes.runtime_exit;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import static org.hyperskill.hstest.common.JavaUtils.isSystemExitAllowed;

class TestSystemExitDynamicMethodMain {
    public static void main(String[] args) {
        System.exit(0);
    }
}

public class TestSystemExitDynamicMethod extends UserErrorTest {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(isSystemExitAllowed());
    }

    @ContainsMessage
    String m = "Wrong answer in test #1";

    @DynamicTestingMethod
    CheckResult test() {
        TestedProgram pr = new TestedProgram(TestSystemExitDynamicMethodMain.class);
        pr.start();
        return CheckResult.wrong("");
    }
}

