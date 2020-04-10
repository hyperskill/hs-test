package org.hyperskill.hstest.dynamic.output;

import org.hyperskill.hstest.dynamic.TestingSecurityManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class SystemOutMock extends OutputStream {

    // original stream is used to actually see
    // the test in the console and nothing else
    private final OutputStream original;

    // cloned stream is used to collect all output
    // from the test and redirect to check function
    private final ByteArrayOutputStream cloned = new ByteArrayOutputStream();

    // dynamic stream contains not only output
    // but also injected input from the test
    private final ByteArrayOutputStream dynamic = new ByteArrayOutputStream();

    // partial stream is used to collect output between
    // dynamic input calls in SystemInMock
    private final Map<ThreadGroup, ByteArrayOutputStream> partial = new HashMap<>();

    SystemOutMock(OutputStream originalStream) {
        this.original = originalStream;
    }

    @Override
    public synchronized void write(int b) throws IOException {
        original.write(b);
        cloned.write(b);
        dynamic.write(b);

        ThreadGroup currGroup = TestingSecurityManager.getTestingGroup();
        if (!partial.containsKey(currGroup)) {
            partial.put(currGroup, new ByteArrayOutputStream());
        }
        partial.get(currGroup).write(b);
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
        dynamic.reset();
        partial.clear();
    }

    public ByteArrayOutputStream getClonedOut() {
        return cloned;
    }

    public ByteArrayOutputStream getDynamicOut() {
        return dynamic;
    }

    public ByteArrayOutputStream getPartialOut(ThreadGroup group) {
        if (!partial.containsKey(group)) {
            return new ByteArrayOutputStream();
        }
        return partial.get(group);
    }
}
