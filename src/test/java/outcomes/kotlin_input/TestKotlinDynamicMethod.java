package outcomes.kotlin_input;

import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.IOException;

import static org.hyperskill.hstest.dynamic.input.KotlinInput.readLine;

class TestKotlinDynamicMethodMain {
    public static void main(String[] args) throws IOException {
        System.out.println(readLine() + "0000");
        System.out.println(readLine() + "1111");
        System.out.println(readLine() + "2222");
    }
}

public class TestKotlinDynamicMethod extends StageTest<String> {
    @DynamicTestingMethod
    CheckResult test1() {
        TestedProgram pr = new TestedProgram(TestKotlinDynamicMethodMain.class);
        String out1 = pr.start();
        String out2 = pr.execute("1234");
        if (!out2.equals("12340000\n")) {
            throw new RuntimeException();
        }
        String out3 = pr.execute("2345");
        if (!out3.equals("23451111\n")) {
            throw new RuntimeException();
        }
        String out4 = pr.execute("3456");
        return new CheckResult(
            "12340000\n23451111\n34562222\n".equals(out1 + out2 + out3 + out4), "");
    }

    @DynamicTestingMethod
    CheckResult test2() {
        TestedProgram pr = new TestedProgram(TestKotlinDynamicMethodMain.class);
        String out1 = pr.start();
        String out2 = pr.execute("4321");
        if (!out2.equals("43210000\n")) {
            throw new RuntimeException();
        }
        String out3 = pr.execute("5432");
        if (!out3.equals("54321111\n")) {
            throw new RuntimeException();
        }
        String out4 = pr.execute("6543");
        return new CheckResult(
            "43210000\n54321111\n65432222\n".equals(out1 + out2 + out3 + out4), "");
    }
}
