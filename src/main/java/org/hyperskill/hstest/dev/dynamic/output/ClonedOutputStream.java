package org.hyperskill.hstest.dev.dynamic.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ClonedOutputStream extends OutputStream {

    // original stream used to actually see
    // the test in the console and nothing else
    private final OutputStream originalStream;

    // cloned stream used to collect all output
    // from the test and redirect to check function
    final ByteArrayOutputStream clonedStream = new ByteArrayOutputStream();

    // dynamic stream used to collect output between
    // dynamic input calls in SystemInMock
    final ByteArrayOutputStream dynamicStream = new ByteArrayOutputStream();

    ClonedOutputStream(OutputStream originalStream) {
        this.originalStream = originalStream;
    }

    @Override
    public void write(int b) throws IOException {
        originalStream.write(b);
        clonedStream.write(b);
        dynamicStream.write(b);
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
