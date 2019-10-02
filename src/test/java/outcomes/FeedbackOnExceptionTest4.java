package outcomes;

import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FeedbackOnExceptionTest4 extends BaseStageTest {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");
        throw new Exception();
    }

    public FeedbackOnExceptionTest4() {
        super(FeedbackOnExceptionTest4.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1\n" +
            "\n" +
            "BASE EX thrown\n" +
            "\n" +
            "java.lang.Exception");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
                .feedbackOnException(
                    ArithmeticException.class,
                    "Do not divide by zero!")
                .feedbackOnException(
                    IOException.class,
                    "IOEX thrown"
                )
                .feedbackOnException(
                    Exception.class,
                    "BASE EX thrown"
                )
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.TRUE;
    }
}
