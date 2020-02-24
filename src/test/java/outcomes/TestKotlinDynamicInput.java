package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.v7.dynamic.input.KotlinInput.readLine;


class TestKotlinDynamicInputMain {
    public static void main(String[] args) throws IOException {
        System.out.println(readLine() + "0000");
        System.out.println(readLine() + "1111");
        System.out.println(readLine() + "2222");
    }
}

public class TestKotlinDynamicInput extends StageTest<String> {

    public TestKotlinDynamicInput() {
        super(TestKotlinDynamicInputMain.class);
        needReloadClass = false;
    }

    @Override
    public List<TestCase<String>> generate() {

        List<TestCase<String>> tests = Arrays.asList(
            new TestCase<String>()
                .addInput("1234")
                .addInput(out -> {
                    if (!out.equals("12340000\n")) {
                        throw new RuntimeException();
                    }
                    return "2345";
                })
                .addInput(out -> {
                    if (!out.equals("23451111\n")) {
                        throw new RuntimeException();
                    }
                    return "3456";
                })
                .setAttach("12340000\n23451111\n34562222\n"),

            new TestCase<String>()
                .addInput("4321")
                .addInput(out -> {
                    if (!out.equals("43210000\n")) {
                        throw new RuntimeException();
                    }
                    return "5432";
                })
                .addInput(out -> {
                    if (!out.equals("54321111\n")) {
                        throw new RuntimeException();
                    }
                    return "6543";
                })
                .setAttach("43210000\n54321111\n65432222\n")
        );

        return tests;
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
