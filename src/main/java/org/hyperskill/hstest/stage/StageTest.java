package org.hyperskill.hstest.stage;

import lombok.Getter;
import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.outcomes.Outcome;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestRun;
import org.hyperskill.hstest.testing.runner.AsyncMainMethodRunner;
import org.hyperskill.hstest.testing.runner.TestRunner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hyperskill.hstest.dynamic.input.DynamicTesting.searchDynamicTests;
import static org.hyperskill.hstest.dynamic.output.ColoredOutput.RED_BOLD;
import static org.hyperskill.hstest.dynamic.output.ColoredOutput.RESET;
import static org.junit.Assert.fail;

public abstract class StageTest<AttachType> {

    protected TestRunner runner = new AsyncMainMethodRunner();
    protected AttachType attach = null;

    @Getter private static TestRun currTestRun;
    private final String sourceName;

    static int currTestGlobal = 0;
    public static final String LIB_TEST_PACKAGE = "outcomes.separate_package.";

    public StageTest() {
        this("");
    }

    public StageTest(String sourceName) {
        Package currPackage = getClass().getPackage();

        String strPackage = "";
        if (currPackage != null) {
            strPackage = currPackage.getName();
        }

        if (sourceName.isEmpty() && strPackage.startsWith(LIB_TEST_PACKAGE)) {
            this.sourceName = strPackage;
        } else {
            this.sourceName = sourceName;
        }
    }

    @Deprecated
    public StageTest(Class<?> testedClass) {
        this(testedClass.getName());
    }

    private List<TestRun> initTests() {
        List<TestRun> testRuns = new ArrayList<>();
        List<TestCase<AttachType>> testCases = new ArrayList<>(generate());
        testCases.addAll(searchDynamicTests(this));

        if (testCases.size() == 0) {
            throw new UnexpectedError("No tests found");
        }

        int currTest = 0;
        int testCount = testCases.size();
        for (TestCase<AttachType> testCase : testCases) {
            testCase.setSourceName(sourceName);
            if (testCase.getCheckFunc() == null) {
                testCase.setCheckFunc(this::check);
            }
            if (testCase.getAttach() == null) {
                testCase.setAttach(attach);
            }
            testRuns.add(new TestRun(++currTest, testCount, testCase, runner));
        }

        return testRuns;
    }

    private void printTestNum(int num) {
        String totalTests = num == currTestGlobal ? "" : " (" + currTestGlobal + ")";
        OutputHandler.getRealOut().println(
            RED_BOLD + "\nStart test " + num + totalTests + RESET
        );
    }

    @Test
    public final void start() {
        int currTest = 0;
        boolean needTearDown = false;
        try {
            SystemHandler.setUp();
            List<TestRun> testRuns = initTests();

            for (TestRun testRun : testRuns) {
                currTest++;
                currTestGlobal++;
                printTestNum(currTest);

                if (testRun.isFirstTest()) {
                    testRun.setUp();
                    needTearDown = true;
                }

                currTestRun = testRun;
                CheckResult result = testRun.test();

                if (!result.isCorrect()) {
                    String fullFeedback = result.getFeedback() + "\n\n"
                        + testRun.getTestCase().getFeedback();
                    throw new WrongAnswer(fullFeedback.trim());
                }

                if (testRun.isLastTest()) {
                    needTearDown = false;
                    testRun.tearDown();
                }
            }
        } catch (Throwable ex) {
            if (needTearDown) {
                try {
                    currTestRun.tearDown();
                } catch (Throwable newEx) {
                    if (newEx instanceof OutcomeError) {
                        ex = newEx;
                    }
                }
            }
            Outcome outcome = Outcome.getOutcome(ex, currTest);
            String failText = outcome.toString();
            fail(failText);
        } finally {
            currTestRun = null;
            SystemHandler.tearDownSystem();
        }
    }

    public List<TestCase<AttachType>> generate() {
        return new ArrayList<>();
    }

    public CheckResult check(String reply, AttachType attach) {
        throw new UnexpectedError("Can't check result: override \"check\" method");
    }
}
