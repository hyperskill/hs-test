package org.hyperskill.hstest.v5.stage;

import org.hyperskill.hstest.v5.exception.FailureHandler;
import org.hyperskill.hstest.v5.exception.WrongAnswerException;
import org.hyperskill.hstest.v5.statics.StaticFieldsManager;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;
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

import static org.hyperskill.hstest.v5.common.Utils.*;
import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public abstract class BaseStageTest<AttachType> {

    // testedClass may not be user class
    // for example, in Swing testing it's SwingTest class, not JFrame
    private final Class<?> testedClass;
    private Class userClass;

    private final Object testedObject;
    private Method mainMethod;

    private final List<TestCase<AttachType>> testCases = new ArrayList<>();

    public BaseStageTest(Class testedClass) {
        this(testedClass, null);
    }

    public BaseStageTest(Class testedClass, Object testedObject) {
        this.testedClass = testedClass;
        this.testedObject = testedObject;
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

        try {
            mainMethod = testedClass.getMethod("main", String[].class);
        } catch (NoSuchMethodException ex) {
            throw new Exception("No main method found");
        }

        boolean isMethodStatic = Modifier.isStatic(mainMethod.getModifiers());

        if (!isMethodStatic) {
            throw new Exception("Main method is not static");
        }

        if (testedObject != null) {
            userClass = testedObject.getClass();
        } else {
            userClass = mainMethod.getDeclaringClass();
        }

        String myName = BaseStageTest.class.getName();

        String generateOwner = getClass()
            .getMethod("generate")
            .getDeclaringClass()
            .getName();

        String checkOwner = getClass()
            .getMethod("check", String.class, Object.class)
            .getDeclaringClass()
            .getName();

        boolean overrodeGenerate = !myName.equals(generateOwner);
        boolean overrodeCheck = !myName.equals(checkOwner);

        if (overrodeGenerate) {
            testCases.addAll(generate());
            if (testCases.size() == 0) {
                throw new Exception("No tests provided by \"generate\" method");
            }
        } else {
            throw new Exception("Can't create tests: override \"generate\" method");
        }

        for (TestCase<AttachType> testCase : testCases) {
            if (testCase.getCheckFunc() == null) {
                if (!overrodeCheck) {
                    throw new Exception("Can't check result: override \"check\" method");
                }
                testCase.setCheckFunc(this::check);
            }
        }
    }

    @Test
    public final void start() {
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

                if (!result.isCorrect()) {
                    throw new WrongAnswerException(result.getFeedback());
                }
            }
        } catch (Throwable t) {
            fail(FailureHandler.getFeedback(t, currTest));
        }
    }

    private String run(TestCase<?> test) throws Exception {
        systemIn.provideLines(normalizeLineEndings(test.getInput()).trim());
        systemOut.clearLog();
        mainMethod.invoke(testedObject, new Object[] { test.getArgs().toArray(new String[0]) });
        return normalizeLineEndings(systemOut.getLog());
    }

    private CheckResult checkSolution(TestCase<AttachType> test, String output) {
        return test.getCheckFunc().apply(output, test.getAttach());
    }

    public List<TestCase<AttachType>> generate() {
        return new ArrayList<>();
    }

    public CheckResult check(String reply, AttachType attach) {
        return CheckResult.FALSE;
    }
}
