package org.hyperskill.hstest.dev.stage;

import org.hyperskill.hstest.dev.exception.FailureHandler;
import org.hyperskill.hstest.dev.statics.StaticFieldsManager;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.PredefinedIOTestCase;
import org.hyperskill.hstest.dev.testcase.TestCase;
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
import java.util.concurrent.ExecutorService;

import static org.hyperskill.hstest.dev.common.Utils.*;
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public abstract class BaseStageTest<AttachType> implements StageTest {

    private Class userClass;
    private final Object testedObject;
    private final Method testedMethod;
    private final boolean isTestingMain;

    private boolean overrodeTestCases;
    private boolean overrodePredefinedIO;
    private boolean overrodeCheck;
    private boolean overrodeSolve;

    private final List<TestCase<AttachType>> testCases = new ArrayList<>();
    private final List<PredefinedIOTestCase> predefinedIOTestCases = new ArrayList<>();

    public BaseStageTest(Method testedMethod, boolean isTestingMain) {
        this(testedMethod, null, isTestingMain);
    }

    public BaseStageTest(Method testedMethod, Object testedObject, boolean isTestingMain) {
        this.testedMethod = testedMethod;
        this.testedObject = testedObject;
        this.isTestingMain = isTestingMain;
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

    private void initTests() throws Exception {
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

        if (!overrodeTestCases && !overrodePredefinedIO) {
            throw new Exception("No tests found");
        }

        if (overrodeTestCases && !overrodeSolve && !overrodeCheck) {
            throw new Exception("Can't check TestCases: " +
                "override solve and/or check");
        }
    }

    @Test
    public void start() {
        int currTest = 0;
        try {
            initTests();
            String savingPackage;
            if (userClass.getPackage() != null) {
                savingPackage = userClass.getPackage().getName();
            } else {
                savingPackage = StaticFieldsManager.getTopPackage(userClass);
            }
            StaticFieldsManager.saveStaticFields(savingPackage);
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

                    if (FailureHandler.detectStaticCloneFails()) {
                        errorMessage += "\n\n" + FailureHandler.avoidStaticsMsg;
                    }

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

                    if (FailureHandler.detectStaticCloneFails()) {
                        errorMessage += "\n\n" + FailureHandler.avoidStaticsMsg;
                    }

                    assertTrue(errorMessage, result.isCorrect());
                }
            }
        } catch (Exception | Error ex) {
            fail(FailureHandler.getFeedback(ex, currTest));
        }
    }

    private String run(TestCase<?> test) throws Exception {
        systemIn.provideLines(normalizeLineEndings(test.getInput()).trim());
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
        String resultFeedback = (byChecking.getFeedback() + "\n" + bySolving.getFeedback()).trim();

        return new CheckResult(isCorrect, resultFeedback);
    }

    public List<TestCase<AttachType>> generateTestCases() {
        return new ArrayList<>();
    }

    public List<PredefinedIOTestCase> generatePredefinedInputOutput() {
        return new ArrayList<>();
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
