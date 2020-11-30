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
import org.hyperskill.hstest.exception.testing.TestedProgramFinishedEarly;
import org.hyperskill.hstest.exception.testing.TestedProgramThrewException;
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

public class MainMethodExecutor extends ProgramExecutor {

    private String className;
    private Class<?> runClass;
    private Method methodToInvoke;
    private ThreadGroup group;

    private ExecutorService executor;
    private Future<?> task;

    public MainMethodExecutor() {
        initByNothing();
    }

    public MainMethodExecutor(String sourceName) {
        if (Package.getPackage(sourceName) != null) {
            initByPackageName(sourceName);
        } else {
            initByClassName(sourceName);
        }
    }

    private void initByClassInstance(Class<?> clazz) {
        if (!ReflectionUtils.hasMainMethod(clazz)) {
            if (clazz.getName().startsWith("outcomes.separate_package.")) {
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
            machine.waitState(ProgramState.RUNNING);
            methodToInvoke.invoke(null, new Object[] {args});
            machine.setState(ProgramState.FINISHED);
        } catch (InvocationTargetException ex) {
            if (StageTest.getCurrTestRun().getErrorInTest() == null) {
                // CheckExitCalled is thrown in case of System.exit()
                // consider System.exit() like normal exit
                if (ex.getCause() instanceof ProgramExited) {
                    machine.setState(ProgramState.FINISHED);
                    return;
                }

                StageTest.getCurrTestRun().setErrorInTest(
                    new ExceptionWithFeedback("", getUserException(ex)));
            }
            machine.setState(ProgramState.EXCEPTION_THROWN);
        } catch (IllegalAccessException ex) {
            StageTest.getCurrTestRun().setErrorInTest(ex);
            machine.setState(ProgramState.FINISHED);
        }
    }

    private String waitOutput(String input) {
        if (!isWaitingInput()) {
            throw new UnexpectedError(
                "Tested program is not waiting for the input "
                    + "(state == \"" + machine.getState() + "\")");
        }

        if (noMoreInput) {
            throw new UnexpectedError(
                "Can't input to the tested program - input was prohibited.");
        }

        this.input = input;
        if (inBackground) {
            machine.setState(ProgramState.RUNNING);
            return "";
        }

        machine.setAndWait(ProgramState.RUNNING);
        if (machine.getState() == ProgramState.EXCEPTION_THROWN) {
            throw new TestedProgramThrewException();
        }
        return returnOutputAfterExecution ? getOutput() : "";
    }

    private String waitInput() {
        if (noMoreInput) {
            return null;
        }
        machine.setAndWait(ProgramState.WAITING, ProgramState.RUNNING);
        String inputLocal = input;
        input = null;
        return inputLocal;
    }

    @Override
    public void start(String... args) {
        if (machine.getState() != ProgramState.NOT_STARTED) {
            throw new UnexpectedError("Cannot start the program twice");
        }

        machine.setState(ProgramState.WAITING);
        SystemInHandler.setDynamicInputFunc(group, this::waitInput);

        executor = newDaemonThreadPool(1, group);
        task = executor.submit(() -> invokeMain(args));
    }

    @Override
    public String execute(String input) {

        if (isFinished()) {
            StageTest.getCurrTestRun().setErrorInTest(
                new ErrorWithFeedback("The main method of the class "
                    + methodToInvoke.getDeclaringClass().getSimpleName()
                    + " has unexpectedly terminated"));
            throw new TestedProgramFinishedEarly();
        }

        if (input == null) {
            stopInput();
            return "";
        }

        return waitOutput(input);
    }

    @Override
    public void stop() {
        executor.shutdownNow();
        task.cancel(true);
        group.interrupt();
        synchronized (machine) {
            inBackground = true;
            while (!isFinished()) {
                this.input = null;
                machine.setAndWait(ProgramState.RUNNING);
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
