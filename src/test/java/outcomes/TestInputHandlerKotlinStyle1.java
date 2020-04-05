package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hyperskill.hstest.v7.dynamic.input.KotlinInput.readLine;

class TestInputHandlerKotlinStyle1Main {
    public static void main(String[] args) throws IOException {
        if (!"123".equals(readLine())
            || !"456".equals(readLine())) {
            throw new RuntimeException();
        }
    }
}

public class TestInputHandlerKotlinStyle1 extends StageTest {
    public TestInputHandlerKotlinStyle1() {
        super(TestInputHandlerKotlinStyle1Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .setInput("123\n456\n")
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.correct();
    }
}
