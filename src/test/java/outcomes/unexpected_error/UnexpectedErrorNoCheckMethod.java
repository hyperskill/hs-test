package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

import java.util.Arrays;
import java.util.List;

class UnexpectedErrorNoCheckMethodMain {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

public class UnexpectedErrorNoCheckMethod extends UnexpectedErrorTest {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #1",
        "Can't check result: override \"check\" method"
    };

    public UnexpectedErrorNoCheckMethod() {
        super(UnexpectedErrorNoCheckMethodMain.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(
            new TestCase()
        );
    }
}
