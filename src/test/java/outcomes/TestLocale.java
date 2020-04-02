package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

class TestLocaleMain {
    public static void main(String[] args) {
        System.out.print(Locale.getDefault());
    }
}

public class TestLocale extends StageTest<String> {

    public TestLocale() {
        super(TestLocaleMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<>()
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals("en_US"), "");
    }
}
