package org.hyperskill.hstest.testing.runner;

import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestRun;

import java.lang.reflect.InvocationTargetException;

public class SpringApplicationRunner implements TestRunner {

    private void launchSpringBootApplication(TestCase<?> testCase) {
        String errorMessage = "Cannot start Spring application";
        try {
            SpringTest.main(testCase.getArgs().toArray(new String[0]));
        } catch (InvocationTargetException ex) {
            if (ex.getCause().getClass().getSimpleName().equals("PortInUseException")) {
                errorMessage += "\nMake sure that no other Spring application is running at the moment.";
            }
            throw new ExceptionWithFeedback(errorMessage, ex.getCause());
        } catch (OutcomeError error) {
            throw error;
        } catch (Throwable ex) {
            throw new UnexpectedError(errorMessage, ex);
        }
    }

    @Override
    public CheckResult test(TestRun testRun) {
        TestCase<?> testCase = testRun.getTestCase();

        if (testRun.getTestNum() == 1) {
            launchSpringBootApplication(testCase);
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
