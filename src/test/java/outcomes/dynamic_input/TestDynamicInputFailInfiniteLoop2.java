package outcomes.dynamic_input;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestDynamicInputFailInfiniteLoop2Main {
    public static void main(String[] args) throws IOException {
        while (true) {
            try {
                System.out.println(new Scanner(System.in).nextLine());
            } catch (Exception ex) { }
        }
    }
}

public class TestDynamicInputFailInfiniteLoop2 extends StageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(
            "Wrong answer in test #1\n" +
                "\n" +
                "Wrong"
        );

        exception.expectMessage(not(containsString("Unexpected error")));
    }

    public TestDynamicInputFailInfiniteLoop2() {
        super(TestDynamicInputFailInfiniteLoop2Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase<>().addInput(x -> CheckResult.wrong("Wrong"))
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return super.check(reply, attach);
    }
}
