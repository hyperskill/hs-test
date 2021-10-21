package org.hyperskill.hstest.testing.execution;

import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.dynamic.security.ExitException;
import org.hyperskill.hstest.dynamic.security.TestingSecurityManager;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.exception.outcomes.ExceptionWithFeedback;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.stage.StageTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hyperskill.hstest.common.ProcessUtils.newDaemonThreadPool;
import static org.hyperskill.hstest.common.ReflectionUtils.getMainMethod;
import static org.hyperskill.hstest.exception.FailureHandler.getUserException;
import static org.hyperskill.hstest.stage.StageTest.LIB_TEST_PACKAGE;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.EXCEPTION_THROWN;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.FINISHED;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.NOT_STARTED;
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.RUNNING;

public class MainMethodExecutor extends ProgramExecutor {

    private String className;
    private Class<?> runClass;
    private Method methodToInvoke;
    private ThreadGroup group;

    private ExecutorService executor;
    private Future<?> task;

    private boolean useSeparateClassLoader = true;

    public MainMethodExecutor() {
        String testSourceName = StageTest.getCurrTestRun().getTestCase().getSourceName();
        if (testSourceName.startsWith(LIB_TEST_PACKAGE)) {
            initByName(testSourceName);
        } else {
            initByNothing();
        }
    }

    public MainMethodExecutor(String sourceName) {
        initByName(sourceName);
    }

    private void initByClassInstance(Class<?> clazz) {
        if (!ReflectionUtils.hasMainMethod(clazz)) {
            if (clazz.getName().startsWith(LIB_TEST_PACKAGE)) {
                initByNothing(clazz.getPackage().getName(), false);
            } else {
                initByNothing();
            }
            return;
        }

        runClass = clazz;
    }

    private void initByName(String sourceName) {
        if (Package.getPackage(sourceName) != null) {
            initByPackageName(sourceName);
        } else {
            initByClassName(sourceName);
        }
    }

    private void initByPackageName(String packageName) {
        initByNothing(packageName, false);
    }

    private void initByClassName(String className) {
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            initByClassInstance(clazz);
        } catch (ClassNotFoundException | NoClassDefFoundError ex) {
            initByNothing(className);
        }
    }

    private void initByNothing() {
        initByNothing("");
    }

    private void initByNothing(String userPackage) {
        initByNothing(userPackage, true);
    }

    private void initByNothing(String userPackage, boolean tryEmptyPackage) {
        List<Class<?>> classesWithMainMethod = ReflectionUtils
            .getAllClassesFromPackage(userPackage)
            .stream()
            .filter(ReflectionUtils::hasMainMethod)
            .collect(toList());

        int count = classesWithMainMethod.size();

        String inPackage = "";
        if (!userPackage.isEmpty()) {
            inPackage = " in package \"" + userPackage + "\"";
        }

        if (count == 0) {
            if (tryEmptyPackage) {
                initByNothing("", false);
                return;
            }
            throw new ErrorWithFeedback(
                "Cannot find a class with a main method" + inPackage + ".\n" +
                "Check if you declared it as \"public static void main(String[] args)\".");
        }

        if (count > 1) {
            if (tryEmptyPackage) {
                initByNothing("", false);
                return;
            }

            String allClassesNames = classesWithMainMethod
                .stream()
                .map(Class::getName)
                .sorted()
                .collect(joining(", "));

            throw new ErrorWithFeedback(
                "There are " + count + " classes with main method"
                    + inPackage + ": " + allClassesNames + ".\n"
                    + "Leave only one of them to be executed.");
        }

        initByClassInstance(classesWithMainMethod.get(0));
    }

    private void initMethod() {
        Class<?> clazz = runClass;

        try {
            if (useSeparateClassLoader) {
                ClassLoader cl = new DynamicClassLoader(clazz);

                className = clazz.getName();
                runClass = cl.loadClass(className);
                methodToInvoke = getMainMethod(runClass);
                group = new ThreadGroup(runClass.getSimpleName());
                group.setDaemon(true);

            } else {
                className = clazz.getName();
                methodToInvoke = getMainMethod(runClass);
                group = new ThreadGroup(runClass.getSimpleName());
                group.setDaemon(true);
            }

        } catch (Exception ex) {
            throw new UnexpectedError("Error initializing MainMethodExecutor " + className, ex);
        }
    }

    private void invokeMain(String[] args) {
        try {
            machine.setState(RUNNING);
            methodToInvoke.invoke(null, new Object[] {args});
            machine.setState(FINISHED);
        } catch (InvocationTargetException ex) {
            if (StageTest.getCurrTestRun().getErrorInTest() == null) {
                // ProgramExited is thrown in case of System.exit()
                // consider System.exit() like normal exit
                if (ex.getCause() instanceof ExitException) {
                    machine.setState(FINISHED);
                    return;
                }

                StageTest.getCurrTestRun().setErrorInTest(
                    new ExceptionWithFeedback("", getUserException(ex)));
            }

            machine.setState(EXCEPTION_THROWN);
        } catch (IllegalAccessException ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
            machine.setState(FINISHED);
        }
    }

    @Override
    protected void launch(String... args) {
        initMethod();
        SystemHandler.installHandler(this,
            () -> TestingSecurityManager.getTestingGroup() == group);
        executor = newDaemonThreadPool(1, group);
        task = executor.submit(() -> invokeMain(args));
    }

    @Override
    protected void terminate() {
        executor.shutdownNow();
        task.cancel(true);
        group.interrupt();
        synchronized (machine) {
            while (!isFinished()) {
                this.input = null;
                machine.waitNotState(RUNNING);
                if (isWaitingInput()) {
                    machine.setState(RUNNING);
                }
            }
        }
    }

    @Override
    public String toString() {
        return runClass.getSimpleName();
    }

    /**
     * This method should be used before the "start" or "startInBackground" method.
     *
     * If set to false, after starting the program will be loaded in to a system class loader.
     *
     * If set to true, all classes will be loaded to a separate class loader. It's useful because
     * in this case all static variables will be reset after each run.
     * It's a default behavior.
     */
    public void setUseSeparateClassLoader(boolean value) {
        if (!machine.inState(NOT_STARTED)) {
            throw new UnexpectedError("Cannot change class loader after the program has started");
        }
        this.useSeparateClassLoader = value;
    }

}
