package org.hyperskill.hstest.testing.execution;

import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.ClassSearcher;
import org.hyperskill.hstest.dynamic.DynamicClassLoader;
import org.hyperskill.hstest.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.dynamic.security.ProgramExited;
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
import static org.hyperskill.hstest.testing.execution.ProgramExecutor.ProgramState.RUNNING;

public class MainMethodExecutor extends ProgramExecutor {

    private String className;
    private Class<?> runClass;
    private Method methodToInvoke;
    private ThreadGroup group;

    private ExecutorService executor;
    private Future<?> task;

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
                initByNothing(clazz.getPackage().getName());
            } else {
                initByNothing();
            }
            return;
        }

        ClassLoader dcl = new DynamicClassLoader(clazz);
        try {
            className = clazz.getName();
            runClass = dcl.loadClass(className);
            methodToInvoke = getMainMethod(runClass);
            group = new ThreadGroup(runClass.getSimpleName());
            group.setDaemon(true);
        } catch (Exception ex) {
            throw new UnexpectedError("Error initializing MainMethodExecutor " + className, ex);
        }
    }

    private void initByName(String sourceName) {
        if (Package.getPackage(sourceName) != null) {
            initByPackageName(sourceName);
        } else {
            initByClassName(sourceName);
        }
    }

    private void initByPackageName(String packageName) {
        initByNothing(packageName);
    }

    private void initByClassName(String className) {
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            initByClassInstance(clazz);
        } catch (ClassNotFoundException ex) {
            initByNothing();
        }
    }

    private void initByNothing() {
        initByNothing("");
    }

    private void initByNothing(String userPackage) {
        // TODO use javap and regex "public static( final)? void main\(java\.lang\.String(\[\]|\.\.\.)\)"

        List<Class<?>> classesWithMainMethod = ClassSearcher
            .getClassesForPackage(userPackage)
            .stream()
            .filter(ReflectionUtils::hasMainMethod)
            .collect(toList());

        int count = classesWithMainMethod.size();

        if (count == 0) {
            throw new ErrorWithFeedback("Cannot find a class with a main method.\n" +
                "Check if you declared it as \"public static void main(String[] args)\".");
        }

        if (count > 1) {
            String allClassesNames = classesWithMainMethod
                .stream()
                .map(Class::getSimpleName)
                .collect(joining(", "));

            throw new ErrorWithFeedback(
                "There are " + count + " classes with main method: " + allClassesNames + ".\n"
                    + "Leave only one of them to be executed.");
        }

        initByClassInstance(classesWithMainMethod.get(0));
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
                if (ex.getCause() instanceof ProgramExited) {
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
        SystemInHandler.setDynamicInputFunc(group, this::requestInput);
        executor = newDaemonThreadPool(1, group);
        task = executor.submit(() -> invokeMain(args));
    }

    @Override
    public void stop() {
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
    public String getOutput() {
        return SystemOutHandler.getPartialOutput(group);
    }

    @Override
    public String toString() {
        return runClass.getSimpleName();
    }

}
