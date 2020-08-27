package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.dynamic.TestingSecurityManager;
import org.hyperskill.hstest.exception.outcomes.FatalError;

import java.io.IOException;
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
            throw new FatalError("Cannot change dynamic input function");
        }
        handlers.put(group, new DynamicInputHandler(func));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;
        b[off + 1] = (byte) (c >> 8);

        int i = 2;
        try {
            for (; i < len; i++) {
                if (c == '\n') {
                    break;
                }
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte) c;
                ++i;
                b[off + i] = (byte) (c >> 8);
            }
        } catch (IOException ignored) {
        }
        return i;
    }

    @Override
    public int read() throws IOException {
        return handlers.get(TestingSecurityManager.getTestingGroup()).ejectChar();
    }
}
