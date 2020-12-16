package outcomes.dynamic_input;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hyperskill.hstest.dynamic.input.KotlinInput.readLine;

class TestDynamicInputMain {
    public static void main(String[] args) throws IOException {
        System.out.println(readLine() + "0000");
        System.out.println(readLine() + "1111");
        System.out.println(readLine() + "2222");
    }
}

public class TestDynamicInput extends StageTest<String> {
    public TestDynamicInput() {
        super(TestDynamicInputMain.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput("1234")
                .addInput(out -> {
                    if (!out.equals("12340000\n")) {
                        System.out.println(0/0);
                    }
                    return "2345";
                })
                .addInput(out -> {
                    if (!out.equals("23451111\n")) {
                        System.out.println(0/0);
                    }
                    return "3456";
                })
                .setAttach("12340000\n23451111\n34562222\n"),

            new TestCase<String>()
                .addInput("4321")
                .addInput(out -> {
                    if (!out.equals("43210000\n")) {
                        System.out.println(0/0);
                    }
                    return "5432";
                })
                .addInput(out -> {
                    if (!out.equals("54321111\n")) {
                        System.out.println(0/0);
                    }
                    return "6543";
                })
                .setAttach("43210000\n54321111\n65432222\n")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
