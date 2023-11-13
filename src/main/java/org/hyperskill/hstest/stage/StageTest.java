package org.hyperskill.hstest.stage;

import lombok.Getter;
import org.hyperskill.hstest.checker.CheckLibraryVersion;
import org.hyperskill.hstest.common.FileUtils;
import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.ClassSearcher;
import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.outcomes.Outcome;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestRun;
import org.hyperskill.hstest.testing.execution.MainMethodExecutor;
import org.hyperskill.hstest.testing.execution.process.*;
import org.hyperskill.hstest.testing.runner.AsyncDynamicTestingRunner;
import org.hyperskill.hstest.testing.runner.TestRunner;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hyperskill.hstest.common.FileUtils.hasJavaSolution;
import static org.hyperskill.hstest.common.FileUtils.walkUserFiles;
import static org.hyperskill.hstest.dynamic.input.DynamicTesting.searchDynamicTests;
import static org.hyperskill.hstest.dynamic.output.ColoredOutput.RED_BOLD;
import static org.hyperskill.hstest.dynamic.output.ColoredOutput.RESET;
import static org.junit.Assert.fail;

public abstract class StageTest<AttachType> {

    protected TestRunner runner = null;
    protected AttachType attach = null;
    protected String source = null;

    @Getter private static TestRun currTestRun;
    private final String sourceName;

    static int currTestGlobal = 0;
    public static final String LIB_TEST_PACKAGE = "outcomes.separate_package.";
    private boolean isTests = false;

    public StageTest() {
        this("");
    }

    public StageTest(String sourceName) {
        if (source != null) {
            sourceName = source;
        }

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

    private TestRunner initRunner() {
        if (hasJavaSolution(FileUtils.cwd())) {
            return new AsyncDynamicTestingRunner(MainMethodExecutor.class);
        }

        for (var folder : walkUserFiles(FileUtils.cwd())) {
            for (var file : folder.getFiles()) {
                if (file.getName().endsWith(".cpp")) {
                    return new AsyncDynamicTestingRunner(CppExecutor.class);
                }
                if (file.getName().endsWith(".go")) {
                    return new AsyncDynamicTestingRunner(GoExecutor.class);
                }
                if (file.getName().endsWith(".js")) {
                    return new AsyncDynamicTestingRunner(JavascriptExecutor.class);
                }
                if (file.getName().endsWith(".py")) {
                    return new AsyncDynamicTestingRunner(PythonExecutor.class);
                }
                if (file.getName().endsWith(".sh")) {
                    return new AsyncDynamicTestingRunner(ShellExecutor.class);
                }
            }
        }

        return new AsyncDynamicTestingRunner(MainMethodExecutor.class);
    }

    private List<TestRun> initTests() {
        if (runner == null) {
            runner = initRunner();
        }

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
        OutputHandler.print(RED_BOLD + "\nStart test " + num + totalTests + RESET);
    }

    @Test
    public final void start() {
        int currTest = 0;
        boolean needTearDown = false;
        try {
            SystemHandler.setUp();

            if (ReflectionUtils.isTests(this)) {
                isTests = true;
                ReflectionUtils.setupCwd(this);
            }

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
                    CheckLibraryVersion checkLibraryVersion = new CheckLibraryVersion();
                    checkLibraryVersion.checkVersion();
                    String fullFeedback = result.getFeedback() + "\n\n"
                        + testRun.getTestCase().getFeedback();
                    if (!checkLibraryVersion.isLatestVersion) {
                        fullFeedback += checkLibraryVersion.getFeedback();
                    }
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

            Outcome outcome;
            String failText;
            try {
                outcome = Outcome.getOutcome(ex, currTest);
                failText = outcome.toString();
            } catch (Throwable newEx) {
                try {
                    outcome = Outcome.getOutcome(newEx, currTest);
                    failText = outcome.toString();
                } catch (Throwable ignored) {
                    // no code execution here allowed so not to throw an exception
                    failText = "Unexpected error\n\nCannot check the submission";
                }
            }

            fail(failText);
        } finally {
            currTestRun = null;
            runner = null;
            attach = null;
            source = null;
            try {
                SystemHandler.tearDownSystem();
            } catch (Throwable ignored) { }
        }
    }

    public List<TestCase<AttachType>> generate() {
        return new ArrayList<>();
    }

    public CheckResult check(String reply, AttachType attach) {
        throw new UnexpectedError("Can't check result: override \"check\" method");
    }

    public static void main(String[] args) {
        Class<?>[] tests = ClassSearcher.getClassesForPackage("")
            .stream()
            .filter(StageTest.class::isAssignableFrom)
            .filter(c -> !Modifier.isAbstract(c.getModifiers()))
            .collect(Collectors.toList()).toArray(new Class<?>[]{});

        if (tests.length == 0) {
            Outcome o = Outcome.getOutcome(
                new UnexpectedError("No tests found"), 0);
            System.out.println(o);
            System.exit(-1);
            return;
        }

        Result result = JUnitCore.runClasses(tests);

        if (result.wasSuccessful()) {
            return;
        }

        String failMessage = result.getFailures().get(0).getMessage();
        System.out.println(failMessage);
        System.exit(-1);
    }
}
