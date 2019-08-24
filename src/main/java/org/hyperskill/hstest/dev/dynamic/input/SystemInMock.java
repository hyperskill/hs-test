package org.hyperskill.hstest.dev.dynamic.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.function.Function;

public class SystemInMock extends InputStream {
    private StringReader currentReader;

    List<Function<String, String>> inputTexts;

    void provideText(String text) {
        currentReader = new StringReader(text);
    }

    void setTexts(List<Function<String, String>> texts) {
        inputTexts = texts;
        currentReader = new StringReader("");
    }

    @Override
    public int read() throws IOException {
        int character = currentReader.read();
        if (character == -1) {

        }
        return character;
    }

}
