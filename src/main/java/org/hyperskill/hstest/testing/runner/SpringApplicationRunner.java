package org.hyperskill.hstest.testing.runner;

import org.hyperskill.hstest.exception.outcomes.FatalError;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestRun;

public class SpringApplicationRunner implements TestRunner {
    @Override
    public <T> CheckResult test(TestRun testRun) {
        TestCase<?> testCase = testRun.getTestCase();

        if (testRun.getTestNum() == 1) {
            try {
                SpringTest.main(testCase.getArgs().toArray(new String[0]));
            } catch (Throwable ex) {
                throw new FatalError("Cannot start Spring application", ex);
            }
        }

        try {
            return testCase.getDynamicTesting().handle();
        } catch (Throwable ex) {
            testRun.setErrorInTest(ex);
        }

        Throwable error = testRun.getErrorInTest();

        if (error instanceof TestPassed) {
            return CheckResult.correct();
        } else if (error instanceof WrongAnswer) {
            return CheckResult.wrong(((WrongAnswer) error).getFeedbackText());
        } else {
            return null;
        }
    }
}
