package outcomes;

import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

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
        String dynamicOutput = SystemOutHandler.getOutputWithInputInjected();
        if (!dynamicOutput.equals("Print x and y: >123 456\n>678\nAnother num:\n>248\n")) {
            throw new RuntimeException();
        }
        return CheckResult.TRUE;
    }
}
