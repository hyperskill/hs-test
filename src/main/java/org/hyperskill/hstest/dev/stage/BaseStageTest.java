package org.hyperskill.hstest.dev.stage;

import org.hyperskill.hstest.dev.dynamic.SystemHandler;
import org.hyperskill.hstest.dev.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.dev.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.dev.exception.WrongAnswerException;
import org.hyperskill.hstest.dev.outcomes.Outcome;
import org.hyperskill.hstest.dev.statics.StaticFieldsManager;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.hyperskill.hstest.dev.testcase.TestRun;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

import static org.hyperskill.hstest.dev.common.FileUtils.createFiles;
import static org.hyperskill.hstest.dev.common.FileUtils.deleteFiles;
import static org.hyperskill.hstest.dev.common.ProcessUtils.startThreads;
import static org.hyperskill.hstest.dev.common.ProcessUtils.stopThreads;
import static org.hyperskill.hstest.dev.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.dev.common.Utils.normalizeLineEndings;
import static org.junit.Assert.fail;

public abstract class BaseStageTest<AttachType> {

    // testedClass may not be user class
    // for example, in Swing testing it's SwingTest class, not JFrame
    private final Class<?> testedClass;
    private Class userClass;

    private final Object testedObject;
    private Method mainMethod;

    private final List<TestCase<AttachType>> testCases = new ArrayList<>();

    protected boolean needResetStaticFields = true;

    private static TestRun currTestRun;

    public static TestRun getCurrTestRun() {
        return currTestRun;
    }

    public BaseStageTest(Class testedClass) {
        this(testedClass, null);
    }

    public BaseStageTest(Class testedClass, Object testedObject) {
        this.testedClass = testedClass;
        this.testedObject = testedObject;
    }

    @BeforeClass
    public static void setUp() {
        Locale.setDefault(Locale.US);
        System.setProperty("line.separator", "\n");
    }

    private void initTests() throws Exception {

        mainMethod = getMainMethod(testedClass);

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
            SystemHandler.setUpSystem();
            initTests();

            if (needResetStaticFields) {
                String savingPackage;

                if (userClass.getPackage() != null) {
                    savingPackage = userClass.getPackage().getName();
                } else {
                    savingPackage = StaticFieldsManager.getTopPackage(userClass);
                }

                StaticFieldsManager.saveStaticFields(savingPackage);
            }

            for (TestCase<AttachType> test : testCases) {
                currTest++;
                System.err.println("Start test " + currTest);
                currTestRun = new TestRun(currTest, test);

                createFiles(test.getFiles());
                ExecutorService pool = startThreads(test.getProcesses());

                String output = run(test);
                CheckResult result = checkSolution(test, output);

                stopThreads(test.getProcesses(), pool);
                deleteFiles(test.getFiles());

                if (!result.isCorrect()) {
                    throw new WrongAnswerException(result.getFeedback());
                }

                if (needResetStaticFields) {
                    StaticFieldsManager.resetStaticFields();
                }
            }
            SystemHandler.tearDownSystem();
        } catch (Throwable t) {
            Outcome outcome = Outcome.getOutcome(t, currTest);
            String failText = outcome.toString();
            SystemHandler.tearDownSystem();
            fail(failText);
        }
    }

    private String run(TestCase<AttachType> test) throws Throwable {
        SystemInHandler.setInputFuncs(test.getInputFuncs());
        SystemOutHandler.resetOutput();
        currTestRun.setErrorInTest(null);
        try {
            mainMethod.invoke(testedObject, new Object[] { test.getArgs().toArray(new String[0]) });
        } catch (InvocationTargetException ex) {
            if (currTestRun.getErrorInTest() != null) {
                throw currTestRun.getErrorInTest();
            }
            if (!(ex.getCause() instanceof CheckExitCalled)) {
                throw ex;
            }
            // consider System.exit() like normal exit
        }
        if (currTestRun.getErrorInTest() != null) {
            throw currTestRun.getErrorInTest();
        }
        return normalizeLineEndings(SystemOutHandler.getOutput());
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
