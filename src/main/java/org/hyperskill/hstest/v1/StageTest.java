package org.hyperskill.hstest.v1;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public abstract class StageTest<ClueType> {

    private final Object testedObject;
    private final Method testedMethod;
    boolean isTestingMain = false;

    private final boolean overrodeTestCases;
    private final boolean overrodePredefinedIO;
    private final boolean overrodeCheck;
    private final boolean overrodeSolve;
    private final boolean overrodeCheckSolved;

    private TestCase<ClueType>[] testCases = null;
    private PredefinedIOTestCase[] predefinedIOTestCases = null;

    private static final String currDir =
        System.getProperty("user.dir") + File.separator;

    public StageTest(Method testedMethod) throws Exception {
        this(testedMethod, null);
    }

    public StageTest(Method testedMethod, Object testedObject) throws Exception {
        this.testedMethod = testedMethod;
        this.testedObject = testedObject;

        boolean isMethodStatic = Modifier.isStatic(testedMethod.getModifiers());

        if (!isMethodStatic && testedObject == null) {
            throw new IllegalArgumentException("Provided method is not static " +
                "and object is not provided");
        }

        String myName = StageTest.class.getName();

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

        String checkSolvedOwner = getClass()
            .getMethod("checkSolved", String.class, String.class)
            .getDeclaringClass()
            .getName();

        overrodeTestCases = !myName.equals(testCasesOwner);
        overrodePredefinedIO = !myName.equals(predefinedIOOwner);
        overrodeCheck = !myName.equals(checkOwner);
        overrodeSolve = !myName.equals(solveOwner);
        overrodeCheckSolved = !myName.equals(checkSolvedOwner);

        if (!overrodeTestCases && !overrodePredefinedIO) {
            throw new Exception("No tests provided: override " +
                "generateTestCases and/or generatePredefinedInputOutput");
        }

        if (overrodeTestCases) {
            testCases = generateTestCases();
            if (testCases.length == 0) {
                throw new Exception("No tests provided by " +
                    "generateTestCases method");
            }
        }

        if (overrodePredefinedIO) {
            predefinedIOTestCases = generatePredefinedInputOutput();
            if (predefinedIOTestCases.length == 0) {
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
    public void testMain() {
        int currTest = 0;
        try {
            if (overrodePredefinedIO) {
                for (PredefinedIOTestCase test : predefinedIOTestCases) {
                    currTest++;
                    String output = run(test);
                    boolean result = checkPredefinedIO(output, test.clue);
                    String errorMessage = "Wrong answer in test #" + currTest;
                    assertTrue(errorMessage, result);
                }
            }
            if (overrodeTestCases) {
                for (TestCase<ClueType> test : testCases) {
                    currTest++;
                    String output = run(test);
                    CheckResult result = checkSolution(test, output);
                    String errorMessage = "Wrong answer in test #" + currTest
                        + "\n" + result.feedback;
                    assertTrue(errorMessage, result.isCorrect);
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

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static String filterStackTrace(final String stackTrace) {
        String[] lines = stackTrace.split("\n");
        ArrayList<String> linesToShow = new ArrayList<>();
        for (String line : lines) {
            if (line.contains("org.junit.")) {
                // if user calls System.exit(0) then
                // stacktrace starts with org.junit that
                // should be skipped
                continue;
            }
            if (line.contains("at java.base/jdk.internal.reflect")) {
                // we're showing all user's stack trace
                // up to our reflect methods
                break;
            }
            linesToShow.add(line);
        }
        return linesToShow
            .stream()
            .reduce("", (a, b) -> a + "\n" + b)
            .trim();
    }

    private String run(TestCase test) throws Exception {
        systemIn.provideLines(test.input);
        systemOut.clearLog();
        if (test.args.size() == 0 && isTestingMain) {
            test.addArgument(new String[]{});
        }
        createFiles(test.files);
        testedMethod.invoke(testedObject, test.args.toArray());
        deleteFiles(test.files);
        return systemOut.getLogWithNormalizedLineSeparator();
    }

    private void createFiles(HashMap<String, String> files) {
        files.forEach((filename, content) -> {
            try {
                Files.write(Paths.get(currDir + filename), content.getBytes());
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void deleteFiles(HashMap<String, String> files) {
        files.forEach((filename, content) -> {
            try {
                Files.deleteIfExists(Paths.get(currDir + filename));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    protected String getFile(String name) {
        try {
            return Files.readString(Paths.get(currDir + name));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private CheckResult checkSolution(TestCase<ClueType> test, String output) {

        CheckResult finalResult = new CheckResult(false);

        CheckResult byChecking = new CheckResult(true);
        CheckResult bySolving = new CheckResult(true);

        if (overrodeCheck) {
            byChecking = check(output, test.clue);
        }
        if (overrodeSolve) {
            String solution = solve(test.input);
            if (overrodeCheckSolved) {
                bySolving = checkSolved(output, solution);
            }
            else {
                bySolving.isCorrect = checkPredefinedIO(output, solution);
            }
        }

        finalResult.isCorrect = byChecking.isCorrect && bySolving.isCorrect;
        finalResult.feedback = (byChecking.feedback + "\n" + bySolving.feedback).trim();

        return finalResult;
    }

    private boolean checkPredefinedIO(String reply, String answer) {
        return reply.trim().equals(answer.trim());
    }

    protected void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex) {}
    }

    public TestCase<ClueType>[] generateTestCases() {
        return null;
    }

    public PredefinedIOTestCase[] generatePredefinedInputOutput() {
        return null;
    }

    public CheckResult check(String reply, ClueType clue) {
        return null;
    }

    public String solve(String input) {
        return null;
    }

    public CheckResult checkSolved(String reply, String clue) {
        return null;
    }

}
