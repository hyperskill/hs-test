package outcomes.lib;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;

class TestUniversalAttachAttach {

}

class TestUniversalAttachMain {
    public static void main(String[] args) {

    }
}

public class TestUniversalAttach extends UserErrorTest<TestUniversalAttachAttach> {

    @ContainsMessage
    String s = "Wrong answer in test #5";

    public TestUniversalAttach() {
        super(TestUniversalAttachMain.class);
    }

    TestUniversalAttachAttach own =  new TestUniversalAttachAttach();

    {
        attach = own;
    }

    @Override
    public List<TestCase<TestUniversalAttachAttach>> generate() {
        return Arrays.asList(
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<>(),
            new TestCase<TestUniversalAttachAttach>().setAttach(new TestUniversalAttachAttach())
        );
    }

    @Override
    public CheckResult check(String reply, TestUniversalAttachAttach attach) {
        return new CheckResult(attach == own, "");
    }
}
