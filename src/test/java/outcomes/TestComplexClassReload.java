package outcomes;

import com.google.gson.Gson;
import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class TestComplexClassReloadMain {
    public static void main(String[] args) {
        Gson gson = new Gson();
    }
}

public class TestComplexClassReload extends BaseStageTest<String> {

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
        return CheckResult.TRUE;
    }
}
