package org.hyperskill.hstest.stage;

import lombok.Getter;
import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.output.ColoredOutput;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
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

import static org.hyperskill.hstest.dynamic.input.DynamicTesting.searchDynamicTestingMethods;
import static org.hyperskill.hstest.dynamic.input.DynamicTesting.searchDynamicTestingVariables;
import static org.junit.Assert.fail;

public abstract class StageTest<AttachType> {

    protected Class<? extends TestRunner> runner = AsyncMainMethodRunner.class;

    @Getter private static TestRun currTestRun;
    private final String sourceName;

    public static final String LIB_TEST_PACKAGE = "outcomes.separate_package.";

    public StageTest() {
        this("");
    }

    public StageTest(String sourceName) {
        String currPackage = getClass().getPackage().getName();
        if (sourceName.isEmpty() && currPackage.startsWith(LIB_TEST_PACKAGE)) {
            this.sourceName = currPackage;
        } else {
            this.sourceName = sourceName;
        }
    }

    @Deprecated
    public StageTest(Class<?> testedClass) {
        this(testedClass.getName());
    }

    private List<TestRun> initTests() throws Exception {
        List<TestRun> testRuns = new ArrayList<>();
        List<TestCase<AttachType>> testCases = new ArrayList<>(generate());

        List<DynamicTesting> methods = new ArrayList<>();
        methods.addAll(searchDynamicTestingVariables(this));
        methods.addAll(searchDynamicTestingMethods(this));

        for (DynamicTesting method : methods) {
            testCases.add(new TestCase<AttachType>().setDynamicTesting(method));
        }

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
            testRuns.add(new TestRun(++currTest, testCount, testCase, runner.newInstance()));
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
        throw new UnexpectedError("Can't check result: override \"check\" method");
    }
}
