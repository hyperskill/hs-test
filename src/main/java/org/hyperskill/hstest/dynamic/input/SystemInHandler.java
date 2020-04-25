package org.hyperskill.hstest.dynamic.input;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public final class SystemInHandler {

    private SystemInHandler() { }

    private static final InputStream realIn = System.in;
    private static final SystemInMock mockIn = new SystemInMock();

    public static void replaceSystemIn() {
        System.setIn(mockIn);
    }

    public static void revertSystemIn() {
        System.setIn(realIn);
    }

    public static void setDynamicInputFunc(ThreadGroup group, Function<String, String> func) {
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
