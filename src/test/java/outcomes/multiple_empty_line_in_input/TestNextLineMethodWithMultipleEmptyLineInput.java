package outcomes.multiple_empty_line_in_input;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;


class TestNextLineMethodWithMultipleEmptyLineInputMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("")) {
                System.out.println("empty");
            } else {
                System.out.println(line);
            }
        }
    }
}

public class TestNextLineMethodWithMultipleEmptyLineInput extends StageTest<String> {


    @DynamicTest
    CheckResult test() {

        TestedProgram testedProgram = new TestedProgram(TestNextLineMethodWithMultipleEmptyLineInputMain.class);
        testedProgram.start();

        String output = testedProgram.execute("test");
        if (!output.equals("test\n")) {
            return CheckResult.wrong("");
        }

        output = testedProgram.execute("test\n");
        if (!output.equals("test\n")) {
            return CheckResult.wrong("");
        }

        output = testedProgram.execute("test\n\n");
        if (!output.equals("test\nempty\n")) {
            return CheckResult.wrong("");
        }

        output = testedProgram.execute("test\n\n\n");
        if (!output.equals("test\nempty\nempty\n")) {
            return CheckResult.wrong("");
        }

        output = testedProgram.execute("\ntest\n\n\n");
        if (!output.equals("empty\ntest\nempty\nempty\n")) {
            return CheckResult.wrong("");
        }

        output = testedProgram.execute("test\n\ntest\n\ntest\n\n\n");
        if (!output.equals("test\nempty\ntest\nempty\ntest\nempty\nempty\n")) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }

}
