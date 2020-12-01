package outcomes.separate_package.find_class_by_package_2;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

class Main1 {
    public static void main(String[] args) {
        System.out.print("Class by package 2");
    }
}

public class FindClassByPackage2 extends StageTest {

    public FindClassByPackage2() {
        super("outcomes.separate_package.find_class_by_package_2");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(new TestCase());
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return new CheckResult(reply.equals("Class by package 2"), "");
    }
}
