package org.hyperskill.hstest.v7.testing.runner;

import org.hyperskill.hstest.v7.dynamic.input.DynamicInput;
import org.hyperskill.hstest.v7.testcase.CheckResult;
import org.hyperskill.hstest.v7.testcase.TestCase;

public class AsyncMainMethodRunner implements TestRunner {

    @Override
    public <T> CheckResult test(TestCase<T> testCase) {
        if (testCase.getDynamicInput() == null) {
            DynamicInput converted = DynamicInput.toDynamicInput(
                testCase.getTestedClass(), testCase.getArgs(), testCase.getInputFuncs());
            testCase.setDynamicInput(converted);
        }

        // SystemInHandler.setInputFuncs(testCase.getInputFuncs());

        return testCase.getDynamicInput().handle();

        /*
        runMain(testCase.getArgs(), testCase.getTimeLimit());

        String output = SystemOutHandler.getOutput();

        if (StageTest.getCurrTestRun().getErrorInTest() == null) {
            try {
                return testCase.getCheckFunc().apply(output, testCase.getAttach());
            } catch (WrongAnswer ex) {
                return wrong(ex.getMessage());
            } catch (TestPassed ex) {
                return correct();
            }
        }

        return null;
        */
    }
}
