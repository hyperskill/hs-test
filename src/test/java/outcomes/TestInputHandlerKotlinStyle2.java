package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.v7.dynamic.input.KotlinInput.readLine;

class TestInputHandlerKotlinStyle2Main {
    public static void main(String[] args) throws IOException {
        if (!"qwerty".equals(readLine())
            || !"second".equals(readLine())) {
            throw new RuntimeException();
        }
    }
}

public class TestInputHandlerKotlinStyle2 extends StageTest {
    public TestInputHandlerKotlinStyle2() {
        super(TestInputHandlerKotlinStyle2Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setInput("qwerty\nsecond")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
