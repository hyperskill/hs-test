package org.hyperskill.hstest.v7.dynamic.input;

import org.hyperskill.hstest.v7.exception.outcomes.FatalError;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.testing.TestedProgram;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.hyperskill.hstest.v7.common.Utils.cleanText;

public interface DynamicInput {
    CheckResult handle();

    static DynamicInput toDynamicInput(Class<?> testedClass, List<String> args,
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
                    if (obj instanceof String) {
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

                newInput = cleanText(newInput);
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
                    break;
                }
                program.execute(input);
            }

            return null;
        };
    }
}
