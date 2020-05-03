package org.hyperskill.hstest.dynamic.output;

import org.hyperskill.hstest.dynamic.TestingSecurityManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class SystemOutMock extends OutputStream {

    // original stream is used to actually see
    // the test in the console and nothing else
    private final PrintStream original;

    // cloned stream is used to collect all output
    // from the test and redirect to check function
    private final ByteArrayOutputStream cloned = new ByteArrayOutputStream();

    // dynamic stream contains not only output
    // but also injected input from the test
    private final ByteArrayOutputStream dynamic = new ByteArrayOutputStream();

    // partial stream is used to collect output between
    // dynamic input calls in SystemInMock
    private final Map<ThreadGroup, ByteArrayOutputStream> partial = new HashMap<>();

    SystemOutMock(PrintStream originalStream) {
        boolean printStdout = !Boolean.getBoolean("ignoreStdout");
        this.original = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                if (printStdout) {
                    originalStream.write(b);
                }
            }
        }, true);
    }

    @Override
    public synchronized void write(int b) {
        original.write(b);
        cloned.write(b);
        dynamic.write(b);

        ThreadGroup currGroup = TestingSecurityManager.getTestingGroup();
        partial.putIfAbsent(currGroup, new ByteArrayOutputStream());
        partial.get(currGroup).write(b);
    }

    @Override
    public void flush() {
        original.flush();
    }

    @Override
    public void close() {
        original.close();
    }

    public void injectInput(String input) {
        for (byte b : input.getBytes()) {
            original.write(b);
            dynamic.write(b);
        }
    }

    public void reset() {
        cloned.reset();
        dynamic.reset();
        partial.clear();
    }

    public PrintStream getOriginal() {
        return original;
    }

    public ByteArrayOutputStream getClonedOut() {
        return cloned;
    }

    public ByteArrayOutputStream getDynamicOut() {
        return dynamic;
    }

    public ByteArrayOutputStream getPartialOut(ThreadGroup group) {
        return partial.getOrDefault(group, new ByteArrayOutputStream());
    }
}
