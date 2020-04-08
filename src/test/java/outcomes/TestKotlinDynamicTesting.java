package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.v7.dynamic.input.KotlinInput.readLine;

class TestKotlinDynamicTestingMain {
    public static void main(String[] args) throws IOException {
        System.out.println(readLine() + "0000");
        System.out.println(readLine() + "1111");
        System.out.println(readLine() + "2222");
    }
}

public class TestKotlinDynamicTesting extends StageTest<String> {
    @Override
    public List<TestCase<String>> generate() {

        List<TestCase<String>> tests = Arrays.asList(
            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestKotlinDynamicTestingMain.class);
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
            }),

            new TestCase<String>().setDynamicTesting(() -> {
                TestedProgram pr = new TestedProgram(TestKotlinDynamicTestingMain.class);
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
            })
        );

        return tests;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
