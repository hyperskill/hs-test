package outcomes;

import com.google.gson.Gson;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class TestComplexClassReloadMain {
    public static void main(String[] args) {
        Gson gson = new Gson();
    }
}

public class TestComplexClassReload extends StageTest<String> {

    public TestComplexClassReload() {
        super(TestComplexClassReloadMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<>(),
            new TestCase<>()
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.correct();
    }
}
