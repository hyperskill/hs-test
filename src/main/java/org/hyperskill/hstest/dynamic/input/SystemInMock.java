package org.hyperskill.hstest.dynamic.input;

import org.hyperskill.hstest.dynamic.TestingSecurityManager;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;
import org.hyperskill.hstest.exception.outcomes.FatalError;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.hyperskill.hstest.common.Utils.cleanText;

public class SystemInMock extends InputStream {
    private StringReader currentReader;
    private List<String> inputLines = new LinkedList<>();

    private Map<ThreadGroup, Function<String, String>>
        dynamicInputFunctions = new HashMap<>();

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

    void setDynamicInputFunction(ThreadGroup group, Function<String, String> func) {
        if (dynamicInputFunctions.containsKey(group)) {
            throw new FatalError("Cannot change dynamic input function");
        }
        dynamicInputFunctions.put(group, func);
        currentReader = new StringReader("");
        inputLines.clear();
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

        int i = 1;
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
            }
        } catch (IOException ignored) {
        }
        return i;
    }

    @Override
    public int read() throws IOException {
        int character = currentReader.read();
        if (character == -1) {
            ejectNextLine();
            character = currentReader.read();
        }
        return character;
    }

    private void ejectNextLine() {
        if (inputLines.isEmpty()) {
            ejectNextInput();
            if (inputLines.isEmpty()) {
                return;
            }
        }
        String nextLine = inputLines.remove(0) + "\n";
        currentReader = new StringReader(nextLine);
        SystemOutHandler.injectInput("> " + nextLine);
    }

    private void ejectNextInput() {
        String currOutput = SystemOutHandler.getPartialOutput(
            TestingSecurityManager.getTestingGroup());

        String newInput = dynamicInputFunctions
            .get(TestingSecurityManager.getTestingGroup())
            .apply(currOutput);

        if (newInput == null) {
            return;
        }
        newInput = cleanText(newInput);
        inputLines.addAll(Arrays.asList(newInput.split("\n")));
    }
}