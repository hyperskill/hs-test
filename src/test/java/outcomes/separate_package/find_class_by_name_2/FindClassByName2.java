package outcomes.separate_package.find_class_by_name_2;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class Main1 {
    public static void main(String[] args) {
        System.out.print("Class by name 2");
    }
}

public class FindClassByName2 extends StageTest {

    public FindClassByName2() {
        super("outcomes.separate_package.find_class_by_name_2.Main1");
    }


    @Override
    public List<TestCase> generate() {
        return Arrays.asList(new TestCase());
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("Class by name 2"), "");
    }
}
