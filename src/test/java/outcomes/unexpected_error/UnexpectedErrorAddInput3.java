package outcomes.unexpected_error;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class UnexpectedErrorAddInput3Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1");
        System.out.println(scanner.nextLine());
    }
}

public class UnexpectedErrorAddInput3 extends StageTest<String> {

    public UnexpectedErrorAddInput3() {
        super(UnexpectedErrorAddInput3Main.class);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(AssertionError.class);
        exception.expectMessage("Unexpected error in test #4");
        exception.expectMessage("UnexpectedError: " +
            "Dynamic input should return String or CheckResult objects only. Found: class java.lang.Integer");
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(out -> {
                    return "12";
                })
                .setAttach("1\n12\n"),

            new TestCase<String>()
                .addInput(out -> {
                    return CheckResult.correct();
                })
                .setAttach("1\n34\n"),

            new TestCase<String>()
                .addInput(out -> {
                    return new CheckResult(out.equals("1\n"), "56");
                })
                .setAttach("1\n56\n"),

            new TestCase<String>()
                .addInput(out -> {
                    return 78;
                })
                .setAttach("1\n78\n")
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {
        return new CheckResult(reply.equals(attach), "");
    }
}
