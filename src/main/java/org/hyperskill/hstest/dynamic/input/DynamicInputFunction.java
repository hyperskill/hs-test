package org.hyperskill.hstest.dynamic.input;

import java.util.function.Function;

public class DynamicInputFunction {

    private int triggerCount;
    private Function<String, Object> inputFunction;

    public DynamicInputFunction(int triggerCount, Function<String, Object> inputFunction) {
        this.triggerCount = triggerCount;
        this.inputFunction = inputFunction;
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public void trigger() {
        triggerCount--;
    }

    public Function<String, Object> getInputFunction() {
        return inputFunction;
    }
}
