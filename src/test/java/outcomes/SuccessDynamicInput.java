package outcomes;

import org.hyperskill.hstest.dev.stage.BaseStageTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SuccessDynamicInput extends BaseStageTest<String> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello");
        String line = scanner.nextLine();
        if (line.equals("1")) {
            System.out.println(scanner.nextLine());
        } else {
            System.out.println(line);
        }
    }

    public SuccessDynamicInput() {
        super(SuccessDynamicInput.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return Arrays.asList(
            new TestCase<String>()
                .addInput(o -> o)
                .setAttach("Hello\nHello\n"),

            new TestCase<String>()
                .addInput(o -> "Hi")
                .setAttach("Hello\nHi\n"),

            new TestCase<String>()
                .addInput(o -> "")
                .addInput(o -> "Hihi")
                .setAttach("Hello\nHihi\n"),

            new TestCase<String>()
                .addInput(o -> "1\n")
                .addInput(o -> "Hey")
                .setAttach("Hello\nHey\n"),

            new TestCase<String>()
                .addInput(o -> "2")
                .addInput(o -> "Hey")
                .setAttach("Hello\n2Hey\n")
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        return new CheckResult(reply.equals(clue));
    }
}
