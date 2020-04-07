package org.hyperskill.hstest.v7.stage;

import org.hyperskill.hstest.v7.dynamic.SystemHandler;
import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.outcomes.FatalError;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.outcomes.Outcome;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestRun;
import org.hyperskill.hstest.v7.testing.TestingConfig;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hyperskill.hstest.v7.dynamic.output.ColoredOutput.RED_BOLD;
import static org.hyperskill.hstest.v7.dynamic.output.ColoredOutput.RESET;
import static org.junit.Assert.fail;

public abstract class StageTest<AttachType> {

    private final Class<?> testedClass;
    private final Object testedObject;

    protected TestingConfig config = new TestingConfig();

    protected boolean needReloadClass = true;

    private static TestRun currTestRun;

    public static TestRun getCurrTestRun() {
        return currTestRun;
    }

    public StageTest(Class<?> testedClass) {
        this(testedClass, null);
    }

    public StageTest(Class<?> testedClass, Object testedObject) {
        this.testedClass = testedClass;
        this.testedObject = testedObject;
    }

    private List<TestRun> initTests() {
        List<TestRun> testRuns = new ArrayList<>();
        List<TestCase<AttachType>> testCases = generate();

        if (testCases.size() == 0) {
            throw new FatalError("No tests provided by \"generate\" method");
        }

        int currTest = 0;
        for (TestCase<AttachType> testCase : testCases) {
            testCase.setTestedClass(testedClass);
            testCase.setTestedObject(testedObject);
            if (testCase.getCheckFunc() == null) {
                testCase.setCheckFunc(this::check);
            }
            testRuns.add(new TestRun(++currTest, testCase));
        }

        return testRuns;
    }

    @Test
    public final void start() {
        int currTest = 0;
        try {
            SystemHandler.setUpSystem();
            List<TestRun> testRuns = initTests();

            for (TestRun testRun : testRuns) {
                currTest++;
                SystemOutHandler.getRealOut().println(
                    RED_BOLD + "\nStart test " + currTest + RESET
                );

                currTestRun = testRun;
                CheckResult result = testRun.test();

                if (!result.isCorrect()) {
                    throw new WrongAnswer(result.getFeedback());
                }
            }
        } catch (Throwable t) {
            Outcome outcome = Outcome.getOutcome(t, currTest);
            String failText = outcome.toString();
            fail(failText);
        } finally {
            currTestRun = null;
            SystemHandler.tearDownSystem();
        }
    }

    public List<TestCase<AttachType>> generate() {
        throw new FatalError("No tests provided by \"generate\" method");
    }

    public CheckResult check(String reply, AttachType attach) {
        throw new FatalError("Can't check result: override \"check\" method");
    }
}
