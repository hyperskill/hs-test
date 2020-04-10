package org.hyperskill.hstest.testing;

import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.runner.TestRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.hyperskill.hstest.common.FileUtils.createFiles;
import static org.hyperskill.hstest.common.FileUtils.deleteFiles;
import static org.hyperskill.hstest.common.ProcessUtils.startThreads;
import static org.hyperskill.hstest.common.ProcessUtils.stopThreads;
import static org.hyperskill.hstest.testcase.CheckResult.correct;

public class TestRun {

    private final TestRunner testRunner;

    private final int testNum;
    private final TestCase<?> testCase;
    private boolean inputUsed = false;

    private Throwable errorInTest = null;

    private List<TestedProgram> testedPrograms = new ArrayList<>();

    public TestRun(int testNum, TestCase<?> testCase, TestRunner testRunner) {
        this.testNum = testNum;
        this.testCase = testCase;
        this.testRunner = testRunner;
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

    public void setInputUsed() {
        inputUsed = true;
    }

    public Throwable getErrorInTest() {
        return errorInTest;
    }

    public void setErrorInTest(Throwable errorInTest) {
        if (this.errorInTest == null) {
            this.errorInTest = errorInTest;
        }
    }

    public void addTestedProgram(TestedProgram testedProgram) {
        testedPrograms.add(testedProgram);
    }

    public void stopTestedPrograms() {
        for (TestedProgram testedProgram : testedPrograms) {
            testedProgram.stop();
        }
    }

    public CheckResult test() throws Throwable {
        createFiles(testCase.getFiles());
        ExecutorService pool = startThreads(testCase.getProcesses());

        SystemOutHandler.resetOutput();
        CheckResult result = testRunner.test(this);

        stopThreads(testCase.getProcesses(), pool);
        deleteFiles(testCase.getFiles());

        if (result == null) {
            checkErrors();
        }

        if (errorInTest instanceof TestPassed) {
            return correct();
        }
        return result;
    }

    private void checkErrors() throws Throwable {
        if (errorInTest == null) {
            return;
        }

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
