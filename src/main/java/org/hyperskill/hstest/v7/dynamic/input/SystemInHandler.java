package org.hyperskill.hstest.v7.dynamic.input;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


public final class SystemInHandler {

    private SystemInHandler() { }

    private static InputStream realIn = System.in;
    private static SystemInMock mockIn = new SystemInMock();

    public static void replaceSystemIn() {
        System.setIn(mockIn);
    }

    public static void revertSystemIn() {
        System.setIn(realIn);
    }

    public static void setInput(String input) {
        mockIn.provideText(input);
    }

    public static void setInputFuncs(List<DynamicInputFunction> inputFuncs) {
        List<DynamicInputFunction> newFuncs = new LinkedList<>();
        for (DynamicInputFunction func : inputFuncs) {
            newFuncs.add(new DynamicInputFunction(
                func.getTriggerCount(), func.getInputFunction()));
        }
        mockIn.setTexts(newFuncs);
    }
}
