package outcomes.dynamic_output;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

class TestDynamicOutputWithoutInputMain {
    public static void main(String[] args) {
        System.out.print("Print x and y: ");
        System.out.println("123 456");
        System.out.println("Another num:");
    }
}

public class TestDynamicOutputWithoutInput extends StageTest<String> {

    public TestDynamicOutputWithoutInput() {
        super(TestDynamicOutputWithoutInputMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #1");
        exception.expectMessage(
                "Please find below the output of your program during this failed test.\n" +
                "\n---\n\n" +
                "Print x and y: 123 456\n" +
                "Another num:"
        );
        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>().setInput("123 456\n678\n248")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return CheckResult.wrong("");
    }
}
