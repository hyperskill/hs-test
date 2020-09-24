package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.dynamic.security.TestingSecurityManager;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SystemInMock extends InputStream {
    private final Map<ThreadGroup, DynamicInputHandler> handlers = new HashMap<>();

    @Deprecated
    void provideText(String text) {
        List<DynamicInputFunction> texts = new LinkedList<>();
        texts.add(new DynamicInputFunction(1, out -> text));
        setTexts(texts);
    }

    @Deprecated
    void setTexts(List<DynamicInputFunction> texts) {
        // inputTextFuncs = texts;
        // TODO setDynamicInputFunction(DynamicInput.toDynamicInput());
    }

    void setDynamicInputFunction(ThreadGroup group, Supplier<String> func) {
        if (handlers.containsKey(group)) {
            throw new UnexpectedError("Cannot change dynamic input function");
        }
        handlers.put(group, new DynamicInputHandler(func));
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
        return handlers.get(TestingSecurityManager.getTestingGroup()).ejectChar();
    }
}
