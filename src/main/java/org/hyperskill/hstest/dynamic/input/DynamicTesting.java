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

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hyperskill.hstest.common.ReflectionUtils.canBeBoxed;
import static org.hyperskill.hstest.common.Utils.cleanText;
import static org.hyperskill.hstest.common.Utils.smartCompare;
import static org.hyperskill.hstest.dynamic.ParametrizedDataExtractor.extractParametrizedData;

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
     * @param sourceName class that is tested. May be a package or a class name
     * @param args arguments for the testedClass's main method
     * @param inputFuncs old way of constructing dynamic testing
     * @return DynamicTesting's single method that provides dynamic input.
     */
    static DynamicTesting toDynamicTesting(String sourceName, List<String> args,
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
            TestedProgram program = new TestedProgram(sourceName);
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
     * and doesn't take any parameters (allowed to take if it is parametrized test).
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
    static <A, T extends StageTest<A>> List<TestCase<A>> searchDynamicTests(T obj) {
        class DynamicTestElement<M extends Member & AnnotatedElement>
            implements Comparable<DynamicTestElement<M>> {

            final List<DynamicTestingWithoutParams> tests;
            final String name;
            int order = 0;
            int repeat = 1;
            int timeLimit = TestCase.DEFAULT_TIME_LIMIT;
            String feedback = "";
            List<Object[]> argsList = new ArrayList<>();

            DynamicTestElement(List<DynamicTestingWithoutParams> tests, M member) {
                this.tests = tests;
                this.name = member.getName();
                fillFields(member);
                checkErrors(member);
            }

            private void fillFields(M elem) {
                if (elem.isAnnotationPresent(DynamicTest.class)) {
                    DynamicTest annotation = elem.getAnnotation(DynamicTest.class);
                    order = annotation.order();
                    timeLimit = annotation.timeLimit();
                    repeat = annotation.repeat();
                    feedback = annotation.feedback();
                    String data = annotation.data();

                    if (elem instanceof Method && !data.isEmpty()) {
                        argsList = extractParametrizedData(data, obj);
                    }
                }
            }

            private void checkErrors(M member) {
                if (repeat < 0) {
                    throw new UnexpectedError("DynamicTest \"" + member.getName()
                        + "\" should not be repeated < 0 times, found " + repeat);
                }

                if (member instanceof Method) {
                    Method method = (Method) member;
                    if (argsList.isEmpty() && method.getParameterCount() != 0) {
                        throw new UnexpectedError("Method \"" + method.getName()
                            + "\" should take 0 arguments. Found: " + method.getParameterCount());
                    }

                    if (!argsList.isEmpty()) {
                        for (Object[] args : argsList) {
                            if (args.length != method.getParameterCount()) {
                                throw new UnexpectedError("Arguments count mismatch: method \""
                                    + method.getName() + "\" should take " + args.length + " parameters, "
                                    + "found " + method.getParameterCount() + ".");
                            }

                            Class<?>[] types = method.getParameterTypes();

                            for (int i = 0; i < types.length; i++) {
                                Class<?> methodType = types[i];
                                Class<?> argsType = args[i].getClass();

                                if (!methodType.isAssignableFrom(argsType)
                                    && !canBeBoxed(methodType, argsType)) {
                                    throw new UnexpectedError("Arguments mismatch: method \""
                                        + method.getName() + "\" should take object of type " + argsType.getName()
                                        + " found " + methodType.getName() + ".");
                                }
                            }
                        }
                    }
                }
            }

            public List<DynamicTesting> getTests() {
                return tests
                    .stream()
                    .flatMap(dt -> {
                        List<DynamicTesting> tests = new ArrayList<>();
                        for (int i = 0; i < repeat; i++) {
                            if (argsList.size() == 0) {
                                tests.add(() -> dt.handle(new Object[]{ }));
                            } else {
                                argsList.forEach(args -> tests.add(() -> dt.handle(args)));
                            }
                        }
                        return tests.stream();
                    })
                    .collect(toList());
            }

            @Override
            public int compareTo(DynamicTestElement o) {
                if (order != o.order) {
                    return order - o.order;
                }
                return smartCompare(name, o.name);
            }
        }

        return Stream.concat(
            Arrays.stream(obj.getClass().getDeclaredMethods()),
            Arrays.stream(obj.getClass().getDeclaredFields()))
            .filter(ReflectionUtils::isDynamicTest)
            .map(member -> {
                List<DynamicTestingWithoutParams> dt = new ArrayList<>();

                if (member instanceof Method) {
                    Method method = (Method) member;
                    if (method.getReturnType() != CheckResult.class) {
                        throw new UnexpectedError("Method \"" + method.getName()
                            + "\" should return CheckResult object. Found: " + method.getReturnType());
                    }
                    dt.add(args -> (CheckResult) ReflectionUtils.invokeMethod(method, obj, args));

                } else if (member instanceof Field) {
                    Field field = (Field) member;
                    ReflectionUtils.getObjectsFromField(field, obj, DynamicTesting.class)
                        .forEach(elem -> dt.add(args -> elem.handle()));

                } else {
                    throw new UnexpectedError("DynamicTest annotation "
                        + " should be applied only to methods and fields.");
                }

                return new DynamicTestElement<>(dt, member);
            })
            .sorted()
            .flatMap(dte -> {
                List<TestCase<A>> tests = new ArrayList<>();
                for (DynamicTesting test : dte.getTests()) {
                    tests.add(new TestCase<A>()
                        .setDynamicTesting(test)
                        .setTimeLimit(dte.timeLimit)
                        .setFeedback(dte.feedback)
                    );
                }
                return tests.stream();
            })
            .collect(toList());
    }
}
