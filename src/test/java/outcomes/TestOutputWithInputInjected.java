package outcomes;

import org.hyperskill.hstest.v6.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.v6.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestOutputWithInputInjected extends BaseStageTest<String> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Print x and y: ");
        scanner.nextInt();
        scanner.nextInt();
        scanner.nextInt();
        System.out.println("Another num:");
        scanner.nextInt();
    }

    public TestOutputWithInputInjected() {
        super(TestOutputWithInputInjected.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("123 456\n678\n248")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        String dynamicOutput = SystemOutHandler.getOutputWithInputInjected();
        if (!dynamicOutput.equals("Print x and y: >123 456\n>678\nAnother num:\n>248\n")) {
            throw new RuntimeException();
        }
        return CheckResult.TRUE;
    }
}
