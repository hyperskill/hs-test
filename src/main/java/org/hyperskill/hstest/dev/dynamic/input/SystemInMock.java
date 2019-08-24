package org.hyperskill.hstest.dev.dynamic.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class SystemInMock extends InputStream {
    private StringReader currentReader;

    void provideText(String text) {
        currentReader = new StringReader(text);
    }

    @Override
    public int read() throws IOException {
        int character = currentReader.read();
        if (character == -1)
            handleEmptyReader();
        return character;
    }

    private void handleEmptyReader() {
        // TODO
    }

}
