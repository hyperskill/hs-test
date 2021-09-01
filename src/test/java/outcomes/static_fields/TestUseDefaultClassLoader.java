package outcomes.static_fields;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.hyperskill.hstest.testing.execution.MainMethodExecutor;
import org.junit.Before;

class TestUseDefaultClassLoaderMain {
    static int x = 0;

    public static void main(String[] args) {
        System.out.print(++x);
    }
}

public class TestUseDefaultClassLoader extends StageTest {

    int x;

    public TestUseDefaultClassLoader() {
        super(TestUseDefaultClassLoaderMain.class);
    }

    @Before
    public void setup() {
        x = TestUseDefaultClassLoaderMain.x;
    }

    @DynamicTest
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestUseDefaultClassLoaderMain.class);
        ((MainMethodExecutor) pr.getProgramExecutor()).setUseSeparateClassLoader(false);
        return new CheckResult(pr.start().equals("" + (x+1)), "");
    }

    @DynamicTest
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestUseDefaultClassLoaderMain.class);
        ((MainMethodExecutor) pr.getProgramExecutor()).setUseSeparateClassLoader(false);
        return new CheckResult(pr.start().equals("" + (x+2)), "");
    }
}
