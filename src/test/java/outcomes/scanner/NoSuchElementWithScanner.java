package outcomes.scanner;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UserErrorTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class NoSuchElementWithScannerMain {
    public static void main(String[] args) {
        new Scanner(System.in).nextInt();
    }
}

public class NoSuchElementWithScanner extends UserErrorTest {

    @ContainsMessage
    String[] m = {
        "Exception in test #1",

        "Probably your program run out of input " +
        "(Scanner tried to read more than expected)."
    };

    public NoSuchElementWithScanner() {
        super(NoSuchElementWithScannerMain.class);
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
