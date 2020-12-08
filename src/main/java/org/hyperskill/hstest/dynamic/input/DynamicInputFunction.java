package org.hyperskill.hstest.dynamic.input;

import lombok.Getter;

import java.util.function.Function;

public class DynamicInputFunction {

    @Getter private int triggerCount;
    @Getter private final Function<String, Object> inputFunction;

    public DynamicInputFunction(int triggerCount, Function<String, Object> inputFunction) {
        this.triggerCount = triggerCount;
        this.inputFunction = inputFunction;
    }

    public void trigger() {
        if (triggerCount > 0) {
            triggerCount--;
        }
    }
}
