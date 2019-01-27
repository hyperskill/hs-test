package org.hyperskill.hstest.stage;

import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.PredefinedIOTestCase;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.List;

public interface StageTest<ClueType> {

    /**
     * Any stage can be started automatically by a testing framework under the hood or manually
     */
    void start();

    default List<TestCase<ClueType>> generateTestCases() {
        return List.of();
    }

    default List<PredefinedIOTestCase> generatePredefinedInputOutput() {
        return List.of();
    }

    default CheckResult check(String reply, ClueType clue) {
        return CheckResult.FALSE;
    }

    default String solve(String input) {
        return "";
    }

    default CheckResult checkSolved(String reply, String clue) {
        return CheckResult.FALSE;
    }
}
