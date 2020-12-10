package org.hyperskill.hstest.dynamic.input;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public final class InputHandler {

    private InputHandler() { }

    private static final InputStream realIn = System.in;
    private static final InputMock mockIn = new InputMock();

    public static void replaceInput() {
        System.setIn(mockIn);
    }

    public static void revertInput() {
        System.setIn(realIn);
    }

    public static void setDynamicInputFunc(ThreadGroup group, DynamicTestFunction func) {
        mockIn.setDynamicInputFunction(group, func);
    }

    @Deprecated
    public static void setInput(String input) {
        mockIn.provideText(input);
    }

    @Deprecated
    public static void setInputFuncs(List<DynamicInputFunction> inputFuncs) {
        List<DynamicInputFunction> newFuncs = new LinkedList<>();
        for (DynamicInputFunction func : inputFuncs) {
            newFuncs.add(new DynamicInputFunction(
                func.getTriggerCount(), func.getInputFunction()));
        }
        mockIn.setTexts(newFuncs);
    }
}
