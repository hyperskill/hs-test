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
            String line = scanner.nextLine();
            if (line.equals("")) {
                System.out.println("Empty line");
            } else {
                System.out.println(line);
            }
            System.out.println("Input line number " + index++);
        }
    }
}

public class TestMultipleLineAtTheEndAndInTheMiddleOfInput extends StageTest<String> {

    private static final Object[][] input = {
        {"\ntest", 2},
        {"\n\ntest\n", 3},
        {"test\ntest\n\n\ntest\n\n\n", 7},
        {"\n\ntest\ntest\ntest\n\n", 6}
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




