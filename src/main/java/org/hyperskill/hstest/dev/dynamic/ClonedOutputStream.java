package org.hyperskill.hstest.dev.dynamic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ClonedOutputStream extends OutputStream {

    private final OutputStream originalStream;
    final ByteArrayOutputStream clonedStream = new ByteArrayOutputStream();

    ClonedOutputStream(OutputStream originalStream) {
        this.originalStream = originalStream;
    }

    @Override
    public void write(int b) throws IOException {
        originalStream.write(b);
        clonedStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        originalStream.flush();
    }

    @Override
    public void close() throws IOException {
        originalStream.close();
    }

}
