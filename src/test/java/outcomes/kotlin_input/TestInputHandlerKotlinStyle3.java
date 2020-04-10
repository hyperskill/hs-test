package outcomes.kotlin_input;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.dynamic.input.KotlinInput.readLine;

class TestInputHandlerKotlinStyle3Main {
    public static void main(String[] args) throws IOException {
        if (!" 123  ".equals(readLine())
            || !"   456    ".equals(readLine())) {
            throw new RuntimeException();
        }
    }
}

public class TestInputHandlerKotlinStyle3 extends StageTest {
    public TestInputHandlerKotlinStyle3() {
        super(TestInputHandlerKotlinStyle3Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setInput(" 123  \n   456    \n")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
