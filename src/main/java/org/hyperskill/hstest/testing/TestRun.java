package org.hyperskill.hstest.testing;

import lombok.Getter;
import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
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
    @Getter private final int testNum;
    @Getter private final int testCount;
    @Getter private final TestCase<?> testCase;

    @Getter private boolean inputUsed = false;
    @Getter Throwable errorInTest = null;
    @Getter private final List<TestedProgram> testedPrograms = new ArrayList<>();

    public TestRun(int testNum, int testCount, TestCase<?> testCase, TestRunner testRunner) {
        this.testNum = testNum;
        this.testCount = testCount;
        this.testCase = testCase;
        this.testRunner = testRunner;
    }

    public final boolean isFirstTest() {
        return testNum == 1;
    }

    public final boolean isLastTest() {
        return testNum == testCount;
    }

    public void setErrorInTest(Throwable errorInTest) {
        if (this.errorInTest == null) {
            this.errorInTest = errorInTest;
        }
    }

    public void setInputUsed() {
        this.inputUsed = true;
    }

    public void addTestedProgram(TestedProgram testedProgram) {
        testedPrograms.add(testedProgram);
    }

    public void stopTestedPrograms() {
        for (TestedProgram testedProgram : testedPrograms) {
            testedProgram.stop();
        }
    }

    public void invalidateHandlers() {
        for (var testedProgram : testedPrograms) {
            SystemHandler.uninstallHandler(testedProgram.getProgramExecutor());
        }
    }

    public final void setUp() {
        testRunner.setUp(testCase);
    }

    public final void tearDown() {
        testRunner.tearDown(testCase);
    }

    public CheckResult test() throws Throwable {
        createFiles(testCase.getFiles());
        ExecutorService pool = startThreads(testCase.getProcesses());

        if (Settings.doResetOutput) {
            OutputHandler.resetOutput();
        }

        CheckResult result = null;
        try {
            result = testRunner.test(this);
        } catch (Throwable ex) {
            setErrorInTest(ex);
        }

        stopThreads(testCase.getProcesses(), pool);
        deleteFiles(testCase.getFiles());

        if (result == null) {
            checkErrors();
        }

        if (errorInTest instanceof TestPassed) {
            result = correct();
        }

        if (result == null) {
            throw new UnexpectedError("Result == null after testing");
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
                if (exClass != null && exClass.isAssignableFrom(userException.getClass())) {
                    throw new ExceptionWithFeedback(feedback, userException);
                }
            }
        }

        throw errorInTest;
    }
}
