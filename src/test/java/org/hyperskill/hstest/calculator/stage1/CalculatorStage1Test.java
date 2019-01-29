package org.hyperskill.hstest.calculator.stage1;

import org.hyperskill.hstest.stage.MainMethodTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.List;
import java.util.Objects;

import static org.hyperskill.hstest.testcase.TestCase.*;

public class CalculatorStage1Test extends MainMethodTest {

    public CalculatorStage1Test() throws Exception {
        super(CalculatorStage1.class);
    }

    /**
     * It generates a list of testcases with a specified input for stdin
     */
    @Override
    public List<TestCase> generateTestCases() {
        return List.of(
                newTestCaseWithInput("0 1"),
                newTestCaseWithInput("1 0"),
                newTestCaseWithInput("2 3"),
                newTestCaseWithInput("100 123"),
                newTestCaseWithInput("-1 5"),
                newTestCaseWithInput("5 -2"),
                newTestCaseWithInput("-300 -400")
        );
    }

    /**
     * It solves the same problem as the program to compare with a learner's solution
     */
    @Override
    public String solve(String input) {
        String[] nums = input.split("\\s+");
        int a = Integer.parseInt(nums[0]);
        int b = Integer.parseInt(nums[1]);
        return Objects.toString(a + b);
    }

    /**
     * It checks our solution equal a learner's solution
     */
    @Override
    public CheckResult checkSolved(String reply, String clue) {
        try {
            int actual = Integer.parseInt(reply.trim());
            int expected = Integer.parseInt(clue.trim());
            return new CheckResult(actual == expected);
        }
        catch (Exception ex) {
            return new CheckResult(false, "Can't check the answer");
        }
    }
}
