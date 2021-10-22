package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.testing.execution.ProgramExecutor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Supplier;

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

    public static ByteArrayOutputStream readline() {
        return mockIn.readline();
    }

    public static void installInputHandler(ProgramExecutor program, Supplier<Boolean> condition) {
        mockIn.installInputHandler(program, condition);
    }

    public static void uninstallInputHandler(ProgramExecutor program) {
        mockIn.uninstallInputHandler(program);
    }

}
