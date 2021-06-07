package outcomes.multiple_empty_line_in_input;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestMultipleLineAtTheEndAndInTheMiddleOfMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int index = 1;

        while (scanner.hasNextLine()) {
            scanner.nextLine();
            System.out.println("Input line number " + index++);
        }
    }
}

public class TestMultipleLineAtTheEndAndInTheMiddleOfInput extends StageTest<String> {

    private static final String[] input = new String[]{
        "\ntest",
        "\n\ntest\n",
        "test\ntest\n\n\ntest\n\n\n",
        "\n\ntest\ntest\ntest\n\n",
    };

    @DynamicTest(data = "input")
    CheckResult testOutput(String input) {

        TestedProgram testedProgram = new TestedProgram(TestMultipleLineAtTheEndAndInTheMiddleOfMain.class);
        testedProgram.start();

        String cleanedInput;

        if (input.endsWith("\n")) {
            cleanedInput = input.substring(0, input.length() - 1);
        } else {
            cleanedInput = input;
        }

        String[] lines = cleanedInput.split("\n", Integer.MAX_VALUE);
        int totalLines = lines.length;

        String output = testedProgram.execute(input);
        if (!output.contains("Input line number " + totalLines) ||
            output.contains("Input line number " + (totalLines + 1))) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}




