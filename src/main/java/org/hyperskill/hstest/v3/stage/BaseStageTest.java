package org.hyperskill.hstest.v3.stage;

import org.hyperskill.hstest.v3.statics.StaticFieldsManager;
import org.hyperskill.hstest.v3.testcase.CheckResult;
import org.hyperskill.hstest.v3.testcase.PredefinedIOTestCase;
import org.hyperskill.hstest.v3.testcase.TestCase;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;

import static org.hyperskill.hstest.v3.common.Utils.*;
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public abstract class BaseStageTest<AttachType> implements StageTest {

    private final Class userClass;
    private final Object testedObject;
    private final Method testedMethod;
    protected boolean isTestingMain = false; // TODO potentially it is possible to make it final

    private final boolean overrodeTestCases;
    private final boolean overrodePredefinedIO;
    private final boolean overrodeCheck;
    private final boolean overrodeSolve;

    private final List<TestCase<AttachType>> testCases = new ArrayList<>();
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

        if (testedObject != null) {
            userClass = testedObject.getClass();
        } else {
            userClass = testedMethod.getDeclaringClass();
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
        System.setProperty("line.separator", "\n");
    }

    @Test
    public void start() {
        int currTest = 0;
        try {
            String topPackage = StaticFieldsManager.getTopPackage(userClass);
            StaticFieldsManager.saveStaticFields(userClass.getPackageName());
            // TODO both loops look very similar
            if (overrodePredefinedIO) {
                for (PredefinedIOTestCase test : predefinedIOTestCases) {
                    currTest++;
                    System.err.println("Start test " + currTest);

                    createFiles(test.getFiles());
                    ExecutorService pool = startThreads(test.getProcesses());

                    String output = run(test);
                    CheckResult result = checkSolved(output, test.getAttach());

                    stopThreads(test.getProcesses(), pool);
                    deleteFiles(test.getFiles());
                    StaticFieldsManager.resetStaticFields();

                    String errorMessage = "Wrong answer in test #" + currTest
                        + "\n\n" + result.getFeedback().trim();
                    assertTrue(errorMessage, result.isCorrect());
                }
            }
            if (overrodeTestCases) {
                for (TestCase<AttachType> test : testCases) {
                    currTest++;
                    System.err.println("Start test " + currTest);

                    createFiles(test.getFiles());
                    ExecutorService pool = startThreads(test.getProcesses());
                    String output = run(test);
                    CheckResult result = checkSolution(test, output);

                    stopThreads(test.getProcesses(), pool);
                    deleteFiles(test.getFiles());
                    StaticFieldsManager.resetStaticFields();

                    String errorMessage = "Wrong answer in test #" + currTest
                        + "\n\n" + result.getFeedback().trim();
                    assertTrue(errorMessage, result.isCorrect());
                }
            }
        } catch (Exception ex) {
            String errorText;
            String stackTraceInfo;
            if (ex.getCause() != null &&
                ex instanceof InvocationTargetException) {
                // If user failed then ex == InvocationTargetException
                // and ex.getCause() == Actual user exception
                errorText = "Exception in test #" + currTest;
                stackTraceInfo = filterStackTrace(getStackTrace(ex.getCause()));

                if (ex.getCause() instanceof NoSuchElementException
                    && stackTraceInfo.contains("java.util.Scanner")) {
                    stackTraceInfo = "Maybe you created more than one instance of Scanner? " +
                        "You should use a single Scanner in program.\n\n" + stackTraceInfo;
                }

                if (stackTraceInfo.contains("java.lang.Runtime.exit")) {
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

    private String run(TestCase<?> test) throws Exception {
        systemIn.provideLines(normalizeLineEndings(test.getInput()).strip());
        systemOut.clearLog();
        if (test.getArgs().size() == 0 && isTestingMain) {
            test.addArgument(new String[]{});
        }
        testedMethod.invoke(testedObject, test.getArgs().toArray());
        return normalizeLineEndings(systemOut.getLog());
    }

    private CheckResult checkSolution(TestCase<AttachType> test, String output) {
        CheckResult byChecking = new CheckResult(true);
        CheckResult bySolving = new CheckResult(true);

        if (overrodeCheck) {
            byChecking = check(output, test.getAttach());
        }
        if (overrodeSolve) {
            String solution = solve(test.getInput());
            bySolving = checkSolved(output, solution);
        }

        boolean isCorrect = byChecking.isCorrect() && bySolving.isCorrect();
        String resultFeedback = byChecking.getFeedback() + "\n" + bySolving.getFeedback().trim();

        return new CheckResult(isCorrect, resultFeedback);
    }

    public List<TestCase<AttachType>> generateTestCases() {
        return List.of();
    }

    public List<PredefinedIOTestCase> generatePredefinedInputOutput() {
        return List.of();
    }

    public CheckResult check(String reply, AttachType clue) {
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
