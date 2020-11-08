package outcomes.unexpected_error;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import outcomes.base.ContainsMessage;
import outcomes.base.UnexpectedErrorTest;

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

public class UnexpectedErrorAddInput3 extends UnexpectedErrorTest<String> {

    @ContainsMessage
    String[] m = {
        "Unexpected error in test #4",

        "UnexpectedError: " +
        "Dynamic input should return String or CheckResult objects only. Found: class java.lang.Integer"
    };

    public UnexpectedErrorAddInput3() {
        super(UnexpectedErrorAddInput3Main.class);
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
