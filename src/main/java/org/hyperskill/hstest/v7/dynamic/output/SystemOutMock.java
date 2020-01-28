package org.hyperskill.hstest.v7.dynamic.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class SystemOutMock extends OutputStream {

    // original stream is used to actually see
    // the test in the console and nothing else
    private final OutputStream original;

    // cloned stream is used to collect all output
    // from the test and redirect to check function
    final ByteArrayOutputStream cloned = new ByteArrayOutputStream();

    // partial stream is used to collect output between
    // dynamic input calls in SystemInMock
    final ByteArrayOutputStream partial = new ByteArrayOutputStream();

    // dynamic stream contains not only output
    // but also injected input from the test
    final ByteArrayOutputStream dynamic = new ByteArrayOutputStream();

    SystemOutMock(OutputStream originalStream) {
        this.original = originalStream;
    }

    @Override
    public void write(int b) throws IOException {
        original.write(b);
        cloned.write(b);
        partial.write(b);
        dynamic.write(b);
    }

    @Override
    public void flush() throws IOException {
        original.flush();
    }

    @Override
    public void close() throws IOException {
        original.close();
    }

    public void injectInput(String input) {
        byte[] inputBytes = input.getBytes();
        try {
            original.write(inputBytes);
            dynamic.write(inputBytes);
        } catch (IOException ignored) { }
    }

    public void reset() {
        cloned.reset();
        partial.reset();
        dynamic.reset();
    }
}
