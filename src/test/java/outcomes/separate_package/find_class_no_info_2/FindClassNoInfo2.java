package outcomes.separate_package.find_class_no_info_2;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class Main1 {
    public static void main(String[] args) {
        System.out.print("Class no Info 2");
    }
}

public class FindClassNoInfo2 extends StageTest {

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(new TestCase());
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("Class no Info 2"), "");
    }
}
