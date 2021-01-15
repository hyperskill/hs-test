package outcomes.dynamic_input;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class TestDynamicInputFailInfiniteLoop2Main {
    public static void main(String[] args) throws IOException {
        while (true) {
            try {
                System.out.println(new Scanner(System.in).nextLine());
            } catch (Exception ex) { }
        }
    }
}

public class TestDynamicInputFailInfiniteLoop2 extends UserErrorTest {

    @ContainsMessage
    String s =
        "Wrong answer in test #1\n" +
        "\n" +
        "Wrong";

    public TestDynamicInputFailInfiniteLoop2() {
        super(TestDynamicInputFailInfiniteLoop2Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<>().addInput(
                x -> CheckResult.wrong("Wrong")
            )
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return super.check(reply, attach);
    }
}
