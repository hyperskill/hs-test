package org.hyperskill.hstest.orders;

import org.hyperskill.hstest.dev.stage.MainMethodTest;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.PredefinedIOTestCase;

import java.util.List;

import static org.hyperskill.hstest.dev.testcase.TestCase.newTestCaseWithPredefinedIO;

public class OrderHistoryAppTest extends MainMethodTest {

    public OrderHistoryAppTest() throws Exception {
        super(OrderHistoryApp.class);
    }

    /**
     * It generates a list of testcases with a specified input for stdin
     */
    @Override
    public List<PredefinedIOTestCase> generatePredefinedInputOutput() {
        return List.of(
                newTestCaseWithPredefinedIO(
                        "apples 100\noranges 200\n/orders\n/exit",
                        "Order: apples, cost: 100\nOrder: oranges, cost: 200\n"
                ),
                newTestCaseWithPredefinedIO(
                        "apples\nexit",
                        "Invalid order"
                )
        );
    }

    /**
     * It checks our solution equal a learner's solution ignoring cases.
     * It also skips all welcome information
     */
    @Override
    public CheckResult checkSolved(String reply, String clue) {
        reply = reply.toUpperCase();
        clue = clue.toUpperCase();
        return new CheckResult(reply.contains(clue));
    }

}
