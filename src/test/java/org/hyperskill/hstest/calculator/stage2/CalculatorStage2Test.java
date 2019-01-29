package org.hyperskill.hstest.calculator.stage2;

import org.hyperskill.hstest.calculator.stage1.CalculatorStage1;
import org.hyperskill.hstest.stage.MainMethodTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.PredefinedIOTestCase;

import java.util.List;

import static org.hyperskill.hstest.testcase.TestCase.newTestCaseWithPredefinedIO;


public class CalculatorStage2Test extends MainMethodTest {

    public CalculatorStage2Test() throws Exception {
        super(CalculatorStage2.class);
    }

    /**
     * It generates several input-output pairs to check a learner's solution
     */
    @Override
    public List<PredefinedIOTestCase> generatePredefinedInputOutput() {
        return List.of(
                newTestCaseWithPredefinedIO("3 4 5 2", "14"),
                newTestCaseWithPredefinedIO("0 1 -1 -5", "-5"),
                newTestCaseWithPredefinedIO("1000 2000 -5000 3000 5000", "6000")
        );
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
