package outcomes.scanner;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

class NoSuchElementWithoutScannerMain {
    public static void main(String[] args) {
        throw new NoSuchElementException();
    }
}

public class NoSuchElementWithoutScanner extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",
        "java.util.NoSuchElementException"
    };

    public NoSuchElementWithoutScanner() {
        super(NoSuchElementWithoutScannerMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        return CheckResult.wrong("");
    }
}
