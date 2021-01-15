package outcomes.lib;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.Settings;
import org.junit.After;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestAllowOutOfInputMain {
    public static void main(String[] args) {
        try {
            new Scanner(System.in).nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

public class TestAllowOutOfInput extends StageTest {

    boolean saved = false;

    @Before
    public void before() {
        saved = Settings.allowOutOfInput;
        Settings.allowOutOfInput = true;
    }

    @After
    public void after() {
        Settings.allowOutOfInput = saved;
    }

    public TestAllowOutOfInput() {
        super(TestAllowOutOfInputMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("No line found\n"), "");
    }
}
