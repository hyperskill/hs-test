package outcomes.lib;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.Settings;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.After;
import org.junit.Before;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

class TestNotResetOutputMain {
    public static void main(String[] args) {
        System.out.println("123");
    }
}

public class TestNotResetOutput extends UserErrorTest {

    @ContainsMessage
    String s =
        "Wrong answer in test #4\n" +
        "\n" +
        "Please find below the output of your program during this failed test.\n" +
        "\n" +
        "---\n" +
        "\n" +
        "123\n" +
        "123\n" +
        "123";

    boolean oldResetOutput;

    @Before
    public void before() {
        oldResetOutput = Settings.doResetOutput;
        Settings.doResetOutput = false;
    }

    @After
    public void after() {
        Settings.doResetOutput = oldResetOutput;
    }

    @DynamicTest(repeat = 3)
    CheckResult test1() {
        new TestedProgram(TestNotResetOutputMain.class).start();
        return CheckResult.correct();
    }

    @DynamicTest()
    CheckResult test2() {
        return CheckResult.wrong("");
    }
}
