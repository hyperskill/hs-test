package org.hyperskill.hstest.dev.dynamic.input;

import org.hyperskill.hstest.dev.dynamic.output.OutputStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class SystemInMock extends InputStream {
    private StringReader currentReader;
    private List<Function<String, String>> inputTextFuncs = new LinkedList<>();

    void provideText(String text) {
        currentReader = new StringReader(text);
    }

    void setTexts(List<Function<String, String>> texts) {
        inputTextFuncs = texts;
        currentReader = new StringReader("");
    }

    @Override
    public int read() throws IOException {
        int character = currentReader.read();
        if (character == -1) {
            if (inputTextFuncs.isEmpty()) {
                return -1;
            }
            String currOutput = OutputStreamHandler.getDynamicOutput();
            Function<String, String> nextFunc = inputTextFuncs.remove(0);
            String newInput = nextFunc.apply(currOutput);
            currentReader = new StringReader(newInput);
            return read();
        }
        return character;
    }

}
