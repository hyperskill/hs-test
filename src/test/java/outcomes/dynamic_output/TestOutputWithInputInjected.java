package outcomes.dynamic_output;

import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestOutputWithInputInjectedMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Print x and y: ");
        scanner.nextInt();
        scanner.nextInt();
        scanner.nextInt();
        System.out.println("Another num:");
        scanner.nextInt();
    }
}

public class TestOutputWithInputInjected extends StageTest<String> {

    public TestOutputWithInputInjected() {
        super(TestOutputWithInputInjectedMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("123 456\n678\n248")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        String dynamicOutput = SystemOutHandler.getDynamicOutput();
        if (!dynamicOutput.equals("Print x and y: > 123 456\n> 678\nAnother num:\n> 248\n")) {
            throw new RuntimeException();
        }
        return CheckResult.correct();
    }
}
