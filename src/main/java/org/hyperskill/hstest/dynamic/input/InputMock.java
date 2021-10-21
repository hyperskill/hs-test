package org.hyperskill.hstest.dynamic.input;

import lombok.Data;
import org.hyperskill.hstest.dynamic.security.ExitException;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.execution.ProgramExecutor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class InputMock extends InputStream {
    @Data
    private static class ConditionalInputHandler {
        final Supplier<Boolean> condition;
        final DynamicInputHandler handler;
    }

    private final Map<ProgramExecutor, ConditionalInputHandler> handlers = new HashMap<>();

    void installInputHandler(ProgramExecutor program, Supplier<Boolean> condition) {
        if (handlers.containsKey(program)) {
            throw new UnexpectedError("Cannot install input handler from the same program twice");
        }
        handlers.put(program, new ConditionalInputHandler(
            condition,
            new DynamicInputHandler(program::requestInput)
        ));
    }

    void uninstallInputHandler(ProgramExecutor program) {
        if (!handlers.containsKey(program)) {
            throw new UnexpectedError("Cannot uninstall input handler that doesn't exist");
        }
        handlers.remove(program);
    }

    private DynamicInputHandler getInputHandler() {
        for (var handler : handlers.values()) {
            if (handler.condition.get()) {
                return handler.handler;
            }
        }

        StageTest.getCurrTestRun().setErrorInTest(
            new UnexpectedError("Cannot find input handler to read data"));
        throw new ExitException(0);
    }

    @Override
    public int read(byte[] b, int off, int len) {
        if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;

        int i = 1;
        for (; i < len; i++) {
            if (c == '\n') {
                break;
            }
            c = read();
            if (c == -1) {
                break;
            }
            b[off + i] = (byte) c;
        }
        return i;
    }

    @Override
    public int read() {
        return getInputHandler().ejectChar();
    }
}
