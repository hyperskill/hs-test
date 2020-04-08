package org.hyperskill.hstest.v7.dynamic.input;

import org.hyperskill.hstest.v7.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.v7.exception.outcomes.FatalError;
import org.hyperskill.hstest.v7.exception.outcomes.OutcomeError;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.testcase.TestCase;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.hyperskill.hstest.v7.common.Utils.cleanText;

/**
 * Interface for creating tests with dynamic input.
 * It is preferred to use dynamic input rather than static.
 * Static input means it's determined before the start of the program,
 * while dynamic input is calculated in the middle of testing process.
 *
 * Dynamic testing has some benefits over tests with static input:
 * 1) You are able to test small parts of the output compared to checking
 *    whole output. Parsing it to determine if the program written correctly
 *    may be more error-prone rather than parsing small pieces of the output.
 * 2) Some programs aren't testable with static input because the input may
 *    be dependent on the previous output (for example, Tic-Tac-Toe game).
 */
public interface DynamicTesting {
    /**
     * Method that provides dynamic testing. Designed to be used with
     * TestedProgram class to be able to partially execute tested program.
     * You can also partially execute multiple programs at the same time.
     * @return CheckResult object at the result of the testing. Return null
     *         if output needs additional testing using StageTest.check method.
     *         Converting old dynamic input to the new one requires returning null
     *         so written tests remain backwards-compatible but it's preferred
     *         to check user's output in DynamicInput.handle method and return
     *         a CheckResult object.
     */
    CheckResult handle();

    /**
     * Converter from old way of constructing dynamic input (multiple methods)
     * to new way (using single method)
     * @param testedClass class that is tested
     * @param args arguments for the testedClass's main method
     * @param inputFuncs old way of constructing dynamic input
     * @return DynamicInput's single method that provides dynamic input.
     */
    static DynamicTesting toDynamicInput(Class<?> testedClass, List<String> args,
                                                List<DynamicInputFunction> inputFuncs) {

        class InputFunctionHandler {
            List<DynamicInputFunction> inputFuncs;

            InputFunctionHandler(List<DynamicInputFunction> inputFuncs) {
                List<DynamicInputFunction> newFuncs = new LinkedList<>();
                for (DynamicInputFunction func : inputFuncs) {
                    newFuncs.add(new DynamicInputFunction(
                        func.getTriggerCount(), func.getInputFunction()));
                }
                this.inputFuncs = newFuncs;
            }

            String ejectNextInput(String currOutput) {
                if (inputFuncs.isEmpty()) {
                    return null;
                }

                DynamicInputFunction inputFunction = inputFuncs.get(0);
                int triggerCount = inputFunction.getTriggerCount();
                if (triggerCount > 0) {
                    inputFunction.trigger();
                }

                // String currOutput = SystemOutHandler.getPartialOutput();
                Function<String, Object> nextFunc = inputFunction.getInputFunction();

                String newInput;
                try {
                    Object obj = nextFunc.apply(currOutput);
                    if (obj instanceof String || obj == null) {
                        newInput = (String) obj;
                    } else if (obj instanceof CheckResult) {
                        CheckResult result = (CheckResult) obj;
                        if (result.isCorrect()) {
                            throw new TestPassed();
                        } else {
                            String errorText = result.getFeedback();
                            throw new WrongAnswer(errorText);
                        }
                    } else {
                        throw new FatalError("Dynamic input should return "
                            + "String or CheckResult objects only. Found: " + obj.getClass());
                    }
                } catch (Throwable throwable) {
                    StageTest.getCurrTestRun().setErrorInTest(throwable);
                    return null;
                }

                if (inputFunction.getTriggerCount() == 0) {
                    inputFuncs.remove(0);
                }

                if (newInput != null) {
                    newInput = cleanText(newInput);
                }

                return newInput;
            }
        }

        return () -> {
            TestedProgram program = new TestedProgram(testedClass);
            String output = program.start(args.toArray(new String[0]));

            InputFunctionHandler handler = new InputFunctionHandler(inputFuncs);

            while (!program.isFinished()) {
                String input = handler.ejectNextInput(output);
                if (input == null) {
                    program.execute(null);
                    break;
                }
                output = program.execute(input);
            }
            program.stop();
            return null;
        };
    }

    /**
     * Searches for methods with annotation DynamicTestingMethod in the obj object
     * and converts this methods into DynamicTesting objects.
     *
     * Requirements for the method: must return CheckResult object
     * and doesn't take any parameters.
     *
     * @param obj object that contain methods declared with DynamicTestingMethod annotation.
     * @return list of DynamicMethod objects that represent every method marked
     *         with DynamicTestingMethod annotation.
     */
    static List<DynamicTesting> searchDynamicTestingMethods(Object obj) {
        List<DynamicTesting> dynamicTestingMethods = new ArrayList<>();

        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(DynamicTestingMethod.class)) {

                if (method.getReturnType() != CheckResult.class) {
                    throw new FatalError("Method " + method.getName()
                        + "should return CheckResult object. Found: " + method.getReturnType());
                }

                if (method.getParameterCount() != 0) {
                    throw new FatalError("Method " + method.getName()
                        + "should take 0 arguments. Found: " + method.getParameterCount());
                }

                dynamicTestingMethods.add(() -> {
                    method.setAccessible(true);
                    try {
                        return (CheckResult) method.invoke(obj);
                    } catch (InvocationTargetException ex) {
                        if (ex.getCause() instanceof Error) {
                            throw (Error) ex.getCause();
                        }
                        throw new FatalError("", ex.getCause());
                    } catch (IllegalAccessException ex) {
                        throw new FatalError("", ex);
                    }
                });
            }
        }
        return dynamicTestingMethods;
    }
}
