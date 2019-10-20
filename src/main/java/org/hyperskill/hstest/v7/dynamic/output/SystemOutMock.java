package org.hyperskill.hstest.v7.dynamic.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class SystemOutMock extends OutputStream {

    // original stream used to actually see
    // the test in the console and nothing else
    private final OutputStream originalStream;

    // cloned stream used to collect all output
    // from the test and redirect to check function
    final ByteArrayOutputStream clonedStream = new ByteArrayOutputStream();

    // dynamic stream used to collect output between
    // dynamic input calls in SystemInMock
    final ByteArrayOutputStream dynamicStream = new ByteArrayOutputStream();

    // this stream contains not only output
    // but also injected input from the test
    final ByteArrayOutputStream
        withInputInjectedStream = new ByteArrayOutputStream();

    SystemOutMock(OutputStream originalStream) {
        this.originalStream = originalStream;
    }

    @Override
    public void write(int b) throws IOException {
        originalStream.write(b);
        clonedStream.write(b);
        dynamicStream.write(b);
        withInputInjectedStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        originalStream.flush();
    }

    @Override
    public void close() throws IOException {
        originalStream.close();
    }

    public void injectInput(String input) {
        byte[] inputBytes = input.getBytes();
        try {
            originalStream.write(inputBytes);
            withInputInjectedStream.write(inputBytes);
        } catch (IOException ignored) { }
    }
}
