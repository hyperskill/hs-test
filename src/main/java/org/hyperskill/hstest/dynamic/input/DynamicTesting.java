package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.common.ReflectionUtils;
import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.TestPassed;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hyperskill.hstest.common.Utils.cleanText;
import static org.hyperskill.hstest.common.Utils.smartCompare;

/**
 * Interface for creating tests with dynamic input.
 * It is preferred to use dynamic input rather than static.
 * Static input means it's determined before the start of the program,
 * while dynamic input is calculated in the middle of testing process.
 *
 * Dynamic testing has some benefits over tests with static input:
 * 1) You are able to test small parts of the output compared to checking whole
 *    output. Parsing whole output to determine if the program written correctly
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
     *         Converting old dynamic testing to the new one requires returning null
     *         so written tests remain backwards-compatible but it's preferred
     *         to check user's output in DynamicTesting.handle method and return
     *         a CheckResult object.
     */
    CheckResult handle();

    /**
     * Converter from old way of constructing dynamic testing (multiple methods)
     * to new way (using single method)
     * @param sourceRun class that is tested. May be a package or a class name
     * @param args arguments for the testedClass's main method
     * @param inputFuncs old way of constructing dynamic testing
     * @return DynamicTesting's single method that provides dynamic input.
     */
    static DynamicTesting toDynamicTesting(String sourceRun, List<String> args,
                                           List<DynamicInputFunction> inputFuncs) {

        class InputFunctionHandler {
            final List<DynamicInputFunction> inputFuncs;

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
                        throw new UnexpectedError("Dynamic input should return "
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
            TestedProgram program = new TestedProgram(sourceRun);
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

            return null;
        };
    }

    /**
     * Searches for methods and variables with annotation DynamicTest in the obj object
     * and converts this methods into DynamicTesting objects.
     *
     * Requirements for the method: must return CheckResult object
     * and doesn't take any parameters.
     *
     * Requirements for the variable: must be a List that contain DynamicTesting objects.
     * or must be an array with type DynamicTesting[]
     *
     * Sorts methods according to their order specified in the DynamicTest annotation,
     * otherwise sorts by their name using Utils::smartCompare method.
     *
     * @param obj object that contain methods and variables declared with DynamicTest annotation.
     * @return list of DynamicMethod objects that represent every method marked
     *         with DynamicTestingMethod annotation.
     */
    static <Attach> List<TestCase<Attach>> searchDynamicTests(Object obj) {
        class DynamicTestElement implements Comparable<DynamicTestElement> {
            final List<DynamicTesting> tests;
            final String name;
            int order = 0;
            int timeLimit = TestCase.DEFAULT_TIME_LIMIT;

            DynamicTestElement(DynamicTesting test, String name) {
                this(Collections.singletonList(test), name);
            }

            DynamicTestElement(List<DynamicTesting> tests, String name) {
                this.tests = tests;
                this.name = name;
            }

            @Override
            public int compareTo(DynamicTestElement o) {
                if (order != o.order) {
                    return order - o.order;
                }
                return smartCompare(name, o.name);
            }
        }

        Stream<DynamicTestElement> testMethods =
            Arrays.stream(obj.getClass().getDeclaredMethods())
                .filter(ReflectionUtils::isDynamicTest)
                .map(method -> {
                    if (method.getReturnType() != CheckResult.class) {
                        throw new UnexpectedError("Method \"" + method.getName()
                            + "\" should return CheckResult object. Found: " + method.getReturnType());
                    } else if (method.getParameterCount() != 0) {
                        throw new UnexpectedError("Method \"" + method.getName()
                            + "\" should take 0 arguments. Found: " + method.getParameterCount());
                    }

                    DynamicTesting dt = () -> (CheckResult) ReflectionUtils.invokeMethod(method, obj);
                    DynamicTestElement dte = new DynamicTestElement(dt, method.getName());

                    // in case it's old annotation we cannot set params
                    if (method.isAnnotationPresent(DynamicTest.class)) {
                        DynamicTest annotation = method.getAnnotation(DynamicTest.class);
                        dte.order = annotation.order();
                        dte.timeLimit = annotation.timeLimit();
                    }

                    return dte;
                });

        Stream<DynamicTestElement> testVariables =
            Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(ReflectionUtils::isDynamicTest)
                .map(field -> {
                    List<DynamicTesting> dt =
                        ReflectionUtils.getObjectsFromField(field, obj, DynamicTesting.class);
                    DynamicTestElement dte = new DynamicTestElement(dt, field.getName());

                    if (field.isAnnotationPresent(DynamicTest.class)) {
                        dte.order = field.getAnnotation(DynamicTest.class).order();
                    }

                    return dte;
                });

        return Stream.concat(testMethods, testVariables)
            .sorted()
            .flatMap(dte -> {
                List<TestCase<Attach>> tests = new ArrayList<>();
                for (DynamicTesting test : dte.tests) {
                    tests.add(new TestCase<Attach>()
                        .setDynamicTesting(test)
                        .setTimeLimit(dte.timeLimit)
                    );
                }
                return tests.stream();
            })
            .collect(toList());
    }
}
