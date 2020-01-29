package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static matcher.FeedbackEquals.feedbackEquals;


class ExceptionInUserCodeMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(1 / 0);
    }
}

public class ExceptionInUserCode extends StageTest {

    public ExceptionInUserCode() {
        super(ExceptionInUserCodeMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage(feedbackEquals(
            "Exception in test #1\n" +
                "\n" +
                "java.lang.ArithmeticException: / by zero\n" +
                "\tat outcomes.ExceptionInUserCodeMain.main(ExceptionInUserCode.java:19)\n" +
                "\n" +
                "Please find below the output of your program during this failed test.\n" +
                "\n" +
                "---\n" +
                "\n" +
                "Hello World"
        ));
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.TRUE;
    }
}
