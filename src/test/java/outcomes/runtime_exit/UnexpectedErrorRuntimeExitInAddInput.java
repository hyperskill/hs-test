package outcomes.runtime_exit;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Assume;
import org.junit.BeforeClass;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hyperskill.hstest.common.JavaUtils.isSystemExitAllowed;

class UnexpectedErrorRuntimeExitInAddInputMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(scanner.nextLine());
    }
}

public class UnexpectedErrorRuntimeExitInAddInput extends UnexpectedErrorTest {

    @BeforeClass
    public static void check() {
        Assume.assumeTrue(isSystemExitAllowed());
    }

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #2",
        "ExitException: Tried to exit"
    };

    public UnexpectedErrorRuntimeExitInAddInput() {
        super(UnexpectedErrorRuntimeExitInAddInputMain.class);
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
