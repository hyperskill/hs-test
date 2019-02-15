package org.hyperskill.hstest.v2.stage;

import org.hyperskill.hstest.v2.testcase.CheckResult;
import org.hyperskill.hstest.v2.testcase.PredefinedIOTestCase;
import org.hyperskill.hstest.v2.testcase.TestCase;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hyperskill.hstest.v2.common.Utils.*;
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public abstract class BaseStageTest<ClueType> implements StageTest {

    private final Object testedObject;
    private final Method testedMethod;
    protected boolean isTestingMain = false; // TODO potentially it is possible to make it final

    private final boolean overrodeTestCases;
    private final boolean overrodePredefinedIO;
    private final boolean overrodeCheck;
    private final boolean overrodeSolve;

    private final List<TestCase<ClueType>> testCases = new ArrayList<>();
    private final List<PredefinedIOTestCase> predefinedIOTestCases = new ArrayList<>();

    public BaseStageTest(Method testedMethod) throws Exception {
        this(testedMethod, null);
    }

    public BaseStageTest(Method testedMethod, Object testedObject) throws Exception {
        this.testedMethod = testedMethod;
        this.testedObject = testedObject;

        boolean isMethodStatic = Modifier.isStatic(testedMethod.getModifiers());

        if (!isMethodStatic && testedObject == null) {
            throw new IllegalArgumentException("Provided method is not static " +
                "and object is not provided");
        }

        // TODO below is a custom polymorphic behaviour implemented that may be skipped in future

        String myName = BaseStageTest.class.getName();

        String testCasesOwner = getClass()
            .getMethod("generateTestCases")
            .getDeclaringClass()
            .getName();

        String predefinedIOOwner = getClass()
            .getMethod("generatePredefinedInputOutput")
            .getDeclaringClass()
            .getName();

        String checkOwner = getClass()
            .getMethod("check", String.class, Object.class)
            .getDeclaringClass()
            .getName();

        String solveOwner = getClass()
            .getMethod("solve", String.class)
            .getDeclaringClass()
            .getName();

        overrodeTestCases = !myName.equals(testCasesOwner);
        overrodePredefinedIO = !myName.equals(predefinedIOOwner);
        overrodeCheck = !myName.equals(checkOwner);
        overrodeSolve = !myName.equals(solveOwner);

        if (!overrodeTestCases && !overrodePredefinedIO) {
            throw new Exception("No tests provided: override " +
                "generateTestCases and/or generatePredefinedInputOutput");
        }

        if (overrodeTestCases) {
            testCases.addAll(generateTestCases());
            if (testCases.size() == 0) {
                throw new Exception("No tests provided by " +
                    "generateTestCases method");
            }
        }

        if (overrodePredefinedIO) {
            predefinedIOTestCases.addAll(generatePredefinedInputOutput());
            if (predefinedIOTestCases.size() == 0) {
                throw new Exception("No tests provided by " +
                    "generatePredefinedInputOutput method");
            }
        }

        if (overrodeTestCases && !overrodeSolve && !overrodeCheck) {
            throw new Exception("Can't check TestCases: " +
                "override solve and/or check");
        }

    }

    @Rule
    public SystemOutRule systemOut = new SystemOutRule().enableLog();

    @Rule
    public TextFromStandardInputStream systemIn = emptyStandardInputStream();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @BeforeClass
    public static void setUp() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void start() {
        int currTest = 0;
        try {
            // TODO both loops look very similar
            if (overrodePredefinedIO) {
                for (PredefinedIOTestCase test : predefinedIOTestCases) {
                    currTest++;
                    String output = run(test);
                    CheckResult result = checkSolved(output, test.getClue());
                    String errorMessage = "Wrong answer in test #" + currTest;
                    assertTrue(errorMessage, result.isCorrect());
                }
            }
            if (overrodeTestCases) {
                for (TestCase<ClueType> test : testCases) {
                    currTest++;
                    String output = run(test);
                    CheckResult result = checkSolution(test, output);
                    String errorMessage = "Wrong answer in test #" + currTest
                        + "\n" + result.getFeedback();
                    assertTrue(errorMessage, result.isCorrect());
                }
            }
        } catch (Exception ex) {
            String errorText;
            String stackTraceInfo;
            if (ex.getCause() != null &&
                ex.toString().equals("java.lang.reflect.InvocationTargetException")) {
                // If user failed then ex == reflect.InvocationTargetException
                // and ex.getCause() == Actual user exception
                errorText = "Exception in test #" + currTest;
                stackTraceInfo = filterStackTrace(getStackTrace(ex.getCause()));

                if (stackTraceInfo.contains("java.lang.System.exit")) {
                    errorText = "Error in test #" + currTest + " - Tried to exit";
                }
            }
            else {
                errorText = "Fatal error in test #" + currTest +
                    ", please send the report to Hyperskill team.";
                if (ex.getCause() == null) {
                    stackTraceInfo = getStackTrace(ex);
                }
                else {
                    stackTraceInfo = getStackTrace(ex) +
                        "\n" + getStackTrace(ex.getCause());
                }
            }

            fail(errorText + "\n\n" + stackTraceInfo);
        }
    }

    private String run(TestCase test) throws Exception {
        systemIn.provideLines(test.getInput());
        systemOut.clearLog();
        if (test.getArgs().size() == 0 && isTestingMain) {
            test.addArgument(new String[]{});
        }
        createFiles(test.files);
        testedMethod.invoke(testedObject, test.getArgs().toArray());
        deleteFiles(test.files);
        return systemOut.getLogWithNormalizedLineSeparator();
    }

    private CheckResult checkSolution(TestCase<ClueType> test, String output) {
        CheckResult byChecking = new CheckResult(true);
        CheckResult bySolving = new CheckResult(true);

        if (overrodeCheck) {
            byChecking = check(output, test.getClue());
        }
        if (overrodeSolve) {
            String solution = solve(test.getInput());
            bySolving = checkSolved(output, solution);
        }

        boolean isCorrect = byChecking.isCorrect() && bySolving.isCorrect();
        String resultFeedback = byChecking.getFeedback() + "\n" + bySolving.getFeedback().trim();

        return new CheckResult(isCorrect, resultFeedback);
    }

    public List<TestCase<ClueType>> generateTestCases() {
        return List.of();
    }

    public List<PredefinedIOTestCase> generatePredefinedInputOutput() {
        return List.of();
    }

    public CheckResult check(String reply, ClueType clue) {
        return CheckResult.FALSE;
    }

    public String solve(String input) {
        return "";
    }

    public CheckResult checkSolved(String reply, String clue) {
        boolean isCorrect = reply.trim().equals(clue.trim());
        return new CheckResult(isCorrect);
    }
}
