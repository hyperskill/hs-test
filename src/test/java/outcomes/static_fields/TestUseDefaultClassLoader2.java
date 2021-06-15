package outcomes.static_fields;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.hyperskill.hstest.testing.execution.MainMethodExecutor;

class TestUseDefaultClassLoader2Main {
    static int x = 0;

    public static void main(String[] args) {
        System.out.print(++x);
    }
}

public class TestUseDefaultClassLoader2 extends StageTest {

    public TestUseDefaultClassLoader2() {
        super(TestUseDefaultClassLoader2Main.class);
    }

    @DynamicTest
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestUseDefaultClassLoader2Main.class);
        ((MainMethodExecutor) pr.getProgramExecutor()).setUseSeparateClassLoader(true);
        return new CheckResult(pr.start().equals("1"), "");
    }

    @DynamicTest
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestUseDefaultClassLoader2Main.class);
        ((MainMethodExecutor) pr.getProgramExecutor()).setUseSeparateClassLoader(true);
        return new CheckResult(pr.start().equals("1"), "");
    }
}
