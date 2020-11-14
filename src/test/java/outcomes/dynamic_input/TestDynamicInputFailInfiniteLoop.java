package outcomes.dynamic_input;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.dynamic.input.KotlinInput.readLine;

class TestDynamicInputFailInfiniteLoopMain {
    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println(readLine());
        }
    }
}

public class TestDynamicInputFailInfiniteLoop extends UserErrorTest {

    @ContainsMessage
    String m =
        "Wrong answer in test #1\n" +
        "\n" +
        "Wrong";

    public TestDynamicInputFailInfiniteLoop() {
        super(TestDynamicInputFailInfiniteLoopMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<>().addInput(x -> CheckResult.wrong("Wrong"))
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return super.check(reply, attach);
    }
}
