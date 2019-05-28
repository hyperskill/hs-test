package org.hyperskill.hstest.android.stage;

import org.hyperskill.hstest.android.exception.FailureHandler;
import org.hyperskill.hstest.android.testcase.CheckResult;
import org.hyperskill.hstest.android.testcase.TestCase;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.hyperskill.hstest.android.common.Utils.*;
import static org.junit.Assert.*;

public abstract class BaseStageTest<AttachType> implements StageTest {

    @Test
    public void start() {
        try {

            TestCase<AttachType> testCase = generateTestCase();
            if (testCase == null) {
                throw new Exception("No tests found");
            }

            System.err.println("Start test");

            createFiles(testCase.getFiles());
            ExecutorService pool = startThreads(testCase.getProcesses());
            CheckResult result = check(testCase.getAttach());

            stopThreads(testCase.getProcesses(), pool);
            deleteFiles(testCase.getFiles());

            String errorMessage = result.getFeedback().trim();

            assertTrue(errorMessage, result.isCorrect());

        } catch (Exception ex) {
            fail(FailureHandler.getFeedback(ex, 0));
        }
    }

    public TestCase<AttachType> generateTestCase() {
        return new TestCase<>();
    }

    public CheckResult check(AttachType clue) {
        return CheckResult.FALSE;
    }
}
