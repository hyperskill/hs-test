package outcomes.multiple_empty_line_in_input;

import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Scanner;

class TestMultipleLineAtTheEndOfInputMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int index = 1;

        while (scanner.hasNextLine()) {
            scanner.nextLine();
            System.out.println("Input line number " + index++);
        }
    }
}

public class TestMultipleLineAtTheEndOfInput extends StageTest<String> {

    private static final Object[][] input = {
        {"test", 1},
        {"test\n", 1},
        {"test\n\n", 2},
        {"test\n\n\n", 3},
        {"test\n\n\n\b", 4}
    };

    @DynamicTest(data = "input")
    CheckResult testOutput(String input, int correctLinesNumbers) {

        TestedProgram testedProgram = new TestedProgram(TestMultipleLineAtTheEndAndInTheMiddleOfMain.class);
        testedProgram.start();


        String output = testedProgram.execute(input);
        if (!output.contains("Input line number " + correctLinesNumbers) ||
            output.contains("Input line number " + (correctLinesNumbers + 1))) {
            return CheckResult.wrong("");
        }

        return CheckResult.correct();
    }
}
