package outcomes.test_passed_thrown;

import org.hyperskill.hstest.exception.outcomes.TestPassed;
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

class TestPassedThrownInCheck2Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class TestPassedThrownInCheck2 extends StageTest<Boolean> {

    public TestPassedThrownInCheck2() {
        super(TestPassedThrownInCheck2Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Wrong answer in test #2\n\n" +
            "test is not passed attach false");
        exception.expectMessage(not(containsString("Unexpected error")));
    }

    @Override
    public List<TestCase<Boolean>> generate() {
        return Arrays.asList(
            new TestCase<Boolean>().setAttach(true),
            new TestCase<Boolean>().setAttach(false)
        );
    }

    @Override
    public CheckResult check(String reply, Boolean attach) {
        if (attach) {
            throw new TestPassed();
        }
        return new CheckResult(false, "test is not passed attach false");
    }
}
