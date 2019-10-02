package org.hyperskill.hstest.v7.dynamic.input;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class SystemInHandler {

    private static InputStream realIn = System.in;
    private static SystemInMock mockIn = new SystemInMock();

    public static void replaceSystemIn() {
        System.setIn(mockIn);
    }

    public static void setInput(String input) {
        mockIn.provideText(input);
    }

    public static void setInputFuncs(List<DynamicInputFunction> inputFuncs) {
        List<DynamicInputFunction> newFuncs = new LinkedList<>(inputFuncs);
        mockIn.setTexts(newFuncs);
    }

    public static void revertSystemIn() {
        System.setIn(realIn);
    }

}
