package outcomes.runtime_exit;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class FatalErrorRuntimeExitInAddInputMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class FatalErrorRuntimeExitInAddInput extends StageTest {

    public FatalErrorRuntimeExitInAddInput() {
        super(FatalErrorRuntimeExitInAddInputMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Fatal error in test #2, please send the report to support@hyperskill.org");
        exception.expectMessage("CheckExitCalled: Tried to exit");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .addInput(out -> {
                    return "123";
                }),

            new TestCase()
                .addInput(out -> {
                    Runtime.getRuntime().exit(0);
                    return "123";
                })
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("123"), "");
    }
}
