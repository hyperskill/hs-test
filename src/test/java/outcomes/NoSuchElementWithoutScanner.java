package outcomes;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


class NoSuchElementWithoutScannerMain {
    public static void main(String[] args) {
        throw new NoSuchElementException();
    }
}

public class NoSuchElementWithoutScanner extends StageTest {

    public NoSuchElementWithoutScanner() {
        super(NoSuchElementWithoutScannerMain.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Exception in test #1");
        exception.expectMessage("java.util.NoSuchElementException");
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.FALSE;
    }
}
