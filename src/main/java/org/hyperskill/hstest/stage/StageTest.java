package org.hyperskill.hstest.stage;

import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.output.ColoredOutput;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.exception.outcomes.FatalError;
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

import static org.junit.Assert.fail;

public abstract class StageTest<AttachType> {

    private final Class<?> testedClass;
    private final Object testedObject;

    protected boolean needReloadClass = true;
    protected Class<? extends TestRunner> runner = AsyncMainMethodRunner.class;

    private static TestRun currTestRun;

    public static TestRun getCurrTestRun() {
        return currTestRun;
    }

    public StageTest() {
        this(null, null);
    }

    public StageTest(Class<?> testedClass) {
        this(testedClass, null);
    }

    public StageTest(Class<?> testedClass, Object testedObject) {
        this.testedClass = testedClass;
        this.testedObject = testedObject;
    }

    private List<TestRun> initTests() throws Exception {
        List<TestRun> testRuns = new ArrayList<>();
        List<TestCase<AttachType>> testCases = generate();

        for (DynamicTesting method : DynamicTesting.searchDynamicTestingMethods(this)) {
            testCases.add(new TestCase<AttachType>().setDynamicTesting(method));
        }

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
            testRuns.add(new TestRun(++currTest, testCase, runner.newInstance()));
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
                    ColoredOutput.RED_BOLD + "\nStart test " + currTest + ColoredOutput.RESET
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
        return new ArrayList<>();
    }

    public CheckResult check(String reply, AttachType attach) {
        throw new FatalError("Can't check result: override \"check\" method");
    }
}
