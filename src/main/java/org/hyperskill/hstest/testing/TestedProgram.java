package org.hyperskill.hstest.testing;

import lombok.Getter;
import org.hyperskill.hstest.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.execution.MainMethodExecutor;
import org.hyperskill.hstest.testing.execution.ProgramExecutor;
import org.hyperskill.hstest.testing.runner.AsyncDynamicTestingRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for running user program and asynchronously test it with input that can be generated while
 * the tested program is running.
 *
 * Supposed to be used inside method marked with @DynamicTest annotation.
 *
 * The main feature is to "freeze" user thread while it's waiting for the input and generate appropriate
 * input based on current output. Then, "freeze" tests while user's program processes this input.
 *
 * The main flow is:
 * 1. Create TestedProgram instance with the class, whose main method you want to run
 * 2. Start the test using TestedProgram::start
 * 3. The tested program will execute till it requests some input
 * 4. "start" returns output that was collected during tested program's partial execution
 * 5. Some testing code generates new input for the tested program
 * 6. Continue testing with the new input using TestedProgram::execute method
 * 7. The tested program will execute till it needs some input etc...
 */
public class TestedProgram {

    @Getter
    private final ProgramExecutor programExecutor;

    @Getter
    private List<String> runArgs;

    /**
     * Creates TestedProgram instance that will search for a class to run
     * and will be able to run it
     */
    public TestedProgram() {
        this((String) null);
    }

    /**
     * Creates TestedProgram instance that will be able to run
     * a particular class if sourceName is a class or a class in
     * a particular package if sourceName is a package name
     */
    public TestedProgram(String sourceName) {
        var runner = StageTest.getCurrTestRun().getTestRunner();

        if (!(runner instanceof AsyncDynamicTestingRunner)) {
            throw new UnexpectedError(
                "TestedProgram is supported only while using " +
                    "AsyncDynamicTestingRunner runner, not " + runner.getClass());
        }

        if (sourceName == null) {
            sourceName = StageTest.getCurrTestRun().getTestCase().getSourceName();
        }

        var asyncRunner = (AsyncDynamicTestingRunner) runner;

        try {
            programExecutor = asyncRunner
                .getExecutor()
                .getConstructor(String.class)
                .newInstance(sourceName);
        } catch (NoSuchMethodException ex) {
            throw new UnexpectedError(
                "Cannot find proper constructor of class " + asyncRunner.getExecutor(), ex);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            if (ex.getCause() instanceof OutcomeError) {
                throw (OutcomeError) ex.getCause();
            }
            throw new UnexpectedError(
                "Cannot instantiate constructor of " + asyncRunner.getExecutor(), ex);
        }
    }

    /**
     * Creates TestedProgram instance, but doesn't run the class
     * It is deprecated, use other constructors instead
     * @param testedClass class, whose main method you want to test
     */
    @Deprecated
    public TestedProgram(Class<?> testedClass) {
        programExecutor = new MainMethodExecutor(testedClass.getName());
    }

    private void initProgram(String... args) {
        this.runArgs = new ArrayList<>(Arrays.asList(args));
        StageTest.getCurrTestRun().addTestedProgram(this);
    }

    /**
     * Shows special feedback in case user's program will throw special exception.
     * Note, that this feedback will be shown only while testing JVM applications,
     * not programs written in Go, JS, other languages.
     * @param clazz class that is supposed to be thrown in the user code
     * @param feedback feedback that will be shown in case such error will be thrown
     */
    public void feedbackOnException(Class<? extends Throwable> clazz, String feedback) {
        StageTest.getCurrTestRun().getTestCase().feedbackOnException(clazz, feedback);
    }

    /**
     * Starts tested program in the background
     * @param args arguments you want tested program to start with
     */
    public void startInBackground(String... args) {
        initProgram(args);
        programExecutor.startInBackground(args);
    }

    /**
     * Starts tested program synchronously, so this method will block test execution
     * till tested program request an input.
     * @param args arguments you want tested program to start with
     * @return Output that tested program manages to print while executing the program.
     *         Returns an empty string if returnOutputAfterExecution is set to false.
     */
    public String start(String... args) {
        initProgram(args);
        return programExecutor.start(args);
    }

    /**
     * @param input input that needs to be sent to the tested program.
     * @return Output that tested program manages to print while executing the program
     *         with the given input.
     *         Returns an empty string in case of:
     *         1. returnOutputAfterExecution is set to false.
     *         2. The execution is done in the background.
     */
    public String execute(String input) {
        return programExecutor.execute(input);
    }

    /**
     * @return Output that tested program is managed to print since the last
     *         "start", "execute" or "getOutput" is invoked.
     *
     *         The TestedProgram class returns every line of the output only once.
     *         So, it is guaranteed that the concatenation of all the strings
     *         that were returned in "start", "execute", "getOutput" methods
     *         will be always equal to the whole output of the tested program.
     */
    public String getOutput() {
        return programExecutor.getOutput();
    }

    /**
     * Stops the tested program and waits it to be finished either by throwing an exception
     * or just by a plain return.
     */
    public void stop() {
        programExecutor.stop();
    }

    /**
     * @return true if the tested program is no longer able to execute any code,
     *         otherwise false
     */
    public boolean isFinished() {
        return programExecutor.isFinished();
    }

    /**
     * If set to false, methods "execute" and "start" will no longer return output but return
     * just an empty string. In this case you can get the output only by "getOutput" method.
     *
     * If set to true, methods "execute" and "start" will return meaningful output.
     * It's default behavior.
     *
     * Notice, that the method "execute" will return an empty string if the program is running
     * in the background regardless of the value of returnOutputAfterExecution.
     */
    public void setReturnOutputAfterExecution(boolean value) {
        programExecutor.setReturnOutputAfterExecution(value);
    }

    /**
     * After this method being called, every input request result in EOF being sent
     * to tested program without waiting for the proper input.
     * If tested program is waiting input, then EOF will also be sent.
     *
     * Note, that this cannot be undone and indicates the end of the input.
     */
    public void stopInput() {
        programExecutor.stopInput();
    }

    /**
     * Returns false if no more input will be consumed by the program.
     * Otherwise, returns true.
     */
    public boolean isInputAllowed() {
        return programExecutor.isInputAllowed();
    }

    /**
     * @return true if tested program waits for the input. Would be useful
     *         for the tested program that is executed in the background.
     */
    public boolean isWaitingInput() {
        return programExecutor.isWaitingInput();
    }

    /**
     * Moves tested program in background mode. Program still be waiting for the input.
     * You can still input data into tested program using "execute" method,
     * but this method return immediately, while tested program will continue to run.
     * Nothing happens if tested program is already in background mode.
     */
    public void goBackground() {
        programExecutor.goBackground();
    }

    /**
     * Moves tested program from background mode into plain sequential mode.
     * If tested program is already waiting for the input then returns immediately.
     * Otherwise waits for the input request and then returns.
     */
    public void stopBackground() {
        programExecutor.stopBackground();
    }

    public boolean isInBackground() {
        return programExecutor.isInBackground();
    }

    @Override
    public String toString() {
        return programExecutor.toString();
    }
}
