package org.hyperskill.hstest.dev.stage;

import org.hyperskill.hstest.dev.dynamic.input.InputStreamHandler;
import org.hyperskill.hstest.dev.dynamic.output.OutputStreamHandler;
import org.hyperskill.hstest.dev.exception.FailureHandler;
import org.hyperskill.hstest.dev.exception.WrongAnswerException;
import org.hyperskill.hstest.dev.statics.StaticFieldsManager;
import org.hyperskill.hstest.dev.testcase.CheckResult;
import org.hyperskill.hstest.dev.testcase.TestCase;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.internal.CheckExitCalled;
import org.junit.contrib.java.lang.system.internal.NoExitSecurityManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

import static java.lang.System.getSecurityManager;
import static org.hyperskill.hstest.dev.common.FileUtils.createFiles;
import static org.hyperskill.hstest.dev.common.FileUtils.deleteFiles;
import static org.hyperskill.hstest.dev.common.ProcessUtils.startThreads;
import static org.hyperskill.hstest.dev.common.ProcessUtils.stopThreads;
import static org.hyperskill.hstest.dev.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.dev.common.Utils.normalizeLineEndings;
import static org.junit.Assert.*;

public abstract class BaseStageTest<AttachType> {

    // testedClass may not be user class
    // for example, in Swing testing it's SwingTest class, not JFrame
    private final Class<?> testedClass;
    private Class userClass;

    private final Object testedObject;
    private Method mainMethod;

    private final List<TestCase<AttachType>> testCases = new ArrayList<>();

    protected boolean needResetStaticFields = true;
    private SecurityManager oldSecurityManager;

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

    private void setUpSystem() throws Exception {
        OutputStreamHandler.replaceSystemOut();
        InputStreamHandler.replaceSystemIn();
        oldSecurityManager = getSecurityManager();
        System.setSecurityManager(
            new NoExitSecurityManager(oldSecurityManager)
        );
    }

    private void tearDownSystem() {
        OutputStreamHandler.revertSystemOut();
        InputStreamHandler.revertSystemIn();
        System.setSecurityManager(oldSecurityManager);
    }

    @Test
    public final void start() {
        int currTest = 0;
        try {
            setUpSystem();
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

                createFiles(test.getFiles());
                ExecutorService pool = startThreads(test.getProcesses());

                String output = run(test);
                CheckResult result = checkSolution(test, output);

                stopThreads(test.getProcesses(), pool);
                deleteFiles(test.getFiles());

                if (needResetStaticFields) {
                    StaticFieldsManager.resetStaticFields();
                }

                if (!result.isCorrect()) {
                    throw new WrongAnswerException(result.getFeedback());
                }
            }
            tearDownSystem();
        } catch (Throwable t) {
            tearDownSystem();
            fail(FailureHandler.getFeedback(t, currTest));
        }
    }

    private String run(TestCase<?> test) throws Exception {
        InputStreamHandler.setInputFuncs(test.getInputFuncs());
        OutputStreamHandler.resetOutput();
        try {
            mainMethod.invoke(testedObject, new Object[] { test.getArgs().toArray(new String[0]) });
        } catch (InvocationTargetException ex) {
            if (!(ex.getCause() instanceof CheckExitCalled)) {
                throw ex;
            }
            // consider System.exit() like normal exit
        }
        return normalizeLineEndings(OutputStreamHandler.getOutput());
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
