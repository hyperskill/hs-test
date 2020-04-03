package org.hyperskill.hstest.v7.testing;

import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.hyperskill.hstest.v7.common.FileUtils.createFiles;
import static org.hyperskill.hstest.v7.common.FileUtils.deleteFiles;
import static org.hyperskill.hstest.v7.common.ProcessUtils.startThreads;
import static org.hyperskill.hstest.v7.common.ProcessUtils.stopThreads;
import static org.hyperskill.hstest.v7.testcase.CheckResult.correct;

public class TestRun {

    private int testNum;
    private TestCase<?> testCase;
    private boolean inputUsed = false;

    private Throwable errorInTest;

    public TestRun(int testNum, TestCase<?> testCase) {
        this.testNum = testNum;
        this.testCase = testCase;
    }

    public int getTestNum() {
        return testNum;
    }

    public TestCase<?> getTestCase() {
        return testCase;
    }

    public boolean isInputUsed() {
        return inputUsed;
    }

    public Throwable getErrorInTest() {
        return errorInTest;
    }

    public void setErrorInTest(Throwable errorInTest) {
        this.errorInTest = errorInTest;
    }

    public void setInputUsed() {
        inputUsed = true;
    }

    public CheckResult test() throws Throwable {
        createFiles(testCase.getFiles());
        ExecutorService pool = startThreads(testCase.getProcesses());

        SystemOutHandler.resetOutput();
        CheckResult result = testCase.runner.test(testCase);

        stopThreads(testCase.getProcesses(), pool);
        deleteFiles(testCase.getFiles());

        checkErrors();

        Throwable errorInTest = StageTest.getCurrTestRun().getErrorInTest();
        if (errorInTest instanceof TestPassed) {
            return correct();
        }
        return result;
    }

    private void checkErrors() throws Throwable {
        if (StageTest.getCurrTestRun().getErrorInTest() == null) {
            return;
        }

        Throwable errorInTest = StageTest.getCurrTestRun().getErrorInTest();

        if (errorInTest instanceof TestPassed) {
            return;
        }

        if (errorInTest instanceof ExceptionWithFeedback) {
            Throwable userException =
                ((ExceptionWithFeedback) errorInTest).getRealException();

            Map<Class<? extends Throwable>, String> feedbackOnExceptions =
                testCase.getFeedbackOnExceptions();

            for (Class<? extends Throwable> exClass : feedbackOnExceptions.keySet()) {
                String feedback = feedbackOnExceptions.get(exClass);
                if (userException != null && exClass.isAssignableFrom(userException.getClass())) {
                    throw new ExceptionWithFeedback(feedback, userException);
                }
            }
        }

        throw errorInTest;
    }
}
