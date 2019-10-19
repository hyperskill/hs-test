package outcomes;

import org.hyperskill.hstest.v7.stage.BaseStageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


class FeedbackOnExceptionTest5Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(0 / 0);
    }
}

public class FeedbackOnExceptionTest5 extends BaseStageTest {

    public FeedbackOnExceptionTest5() {
        super(FeedbackOnExceptionTest5Main.class);
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
            "java.lang.ArithmeticException");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
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
