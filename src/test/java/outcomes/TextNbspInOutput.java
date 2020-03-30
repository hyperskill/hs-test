package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class TextNbspInOutputMain {
    public static void main(String[] args) {
        System.out.print("1\u00a02 3");
    }
}


public class TextNbspInOutput extends StageTest {

    public TextNbspInOutput() {
        super(TextNbspInOutputMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("1\u00202\u00203"), "");
    }
}
