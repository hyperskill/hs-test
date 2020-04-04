package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.dynamic.input.DynamicInput;
import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.v7.exception.outcomes.TestPassed;
import org.hyperskill.hstest.v7.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

import static org.hyperskill.hstest.v7.testcase.CheckResult.correct;
import static org.hyperskill.hstest.v7.testcase.CheckResult.wrong;

public class AsyncMainMethodRunner implements TestRunner {

    @Override
    public <T> CheckResult test(TestCase<T> testCase) {
        if (testCase.getDynamicInput() == null) {
            DynamicInput converted = DynamicInput.toDynamicInput(
                testCase.getTestedClass(), testCase.getArgs(), testCase.getInputFuncs());
            testCase.setDynamicInput(converted);
        }

        CheckResult result = testCase.getDynamicInput().handle();

        if (result == null) {
            Throwable error = StageTest.getCurrTestRun().getErrorInTest();
            try {
                if (error == null) {
                    return testCase.getCheckFunc().apply(
                        SystemOutHandler.getOutput(), testCase.getAttach());
                }
            } catch (Throwable ex) {
                error = ex;
                StageTest.getCurrTestRun().setErrorInTest(error);
            }

            if (error instanceof TestPassed) {
                return correct();
            } else if (error instanceof WrongAnswer) {
                return wrong(error.getMessage());
            } else {
                return null;
            }
        }

        return result;
    }
}
