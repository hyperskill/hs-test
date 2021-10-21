package org.hyperskill.hstest.dynamic.output;

import lombok.Data;
import lombok.Getter;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;
import org.hyperskill.hstest.testing.execution.ProgramExecutor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.hyperskill.hstest.dynamic.output.ColoredOutput.BLUE;
import static org.hyperskill.hstest.dynamic.output.ColoredOutput.RESET;
import static org.hyperskill.hstest.testing.ExecutionOptions.ignoreStdout;

public class OutputMock extends OutputStream {
    @Data
    private static class ConditionalOutput {
        final Supplier<Boolean> condition;
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
    }

    // original stream is used to actually see
    // the test in the console and nothing else
    @Getter private final PrintStream original;

    // cloned stream is used to collect all output
    // from the test and redirect to check function
    private final ByteArrayOutputStream cloned = new ByteArrayOutputStream();

    // dynamic stream contains not only output
    // but also injected input from the test
    private final ByteArrayOutputStream dynamic = new ByteArrayOutputStream();

    // partial stream is used to collect output between
    // dynamic input calls in SystemInMock
    private final Map<ProgramExecutor, ConditionalOutput> partial = new HashMap<>();

    // test output is used to print text inside tests
    // this text will be printed in blue to distinguish it from user's program text
    private final ByteArrayOutputStream testOutput = new ByteArrayOutputStream();

    OutputMock(PrintStream originalStream) {
        this.original = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                if (!ignoreStdout) {
                    originalStream.write(b);
                }
            }
        }, true);
    }

    @Override
    public synchronized void write(int b) {
        var partialHandler = getPartialHandler();

        if (partialHandler == null) {
            testOutput.write(b);
            if (b == '\n') {
                original.print(BLUE + testOutput + RESET);
                testOutput.reset();
            }
            return;
        }

        original.write(b);
        cloned.write(b);
        dynamic.write(b);
        partialHandler.write(b);

        InfiniteLoopDetector.write(b);
    }

    @Override
    public void flush() {
        original.flush();
    }

    @Override
    public void close() {
        original.close();
    }

    public synchronized void injectInput(String input) {
        for (byte b : input.getBytes()) {
            original.write(b);
            dynamic.write(b);
        }
    }

    public void reset() {
        cloned.reset();
        dynamic.reset();
        for (var value : partial.values()) {
            value.getOutput().reset();
        }
        InfiniteLoopDetector.reset();
    }

    public String getCloned() {
        return cloned.toString();
    }

    public String getDynamic() {
        return dynamic.toString();
    }

    public synchronized String getPartial(ProgramExecutor program) {
        ByteArrayOutputStream s = partial.get(program).output;
        String output = s.toString();
        s.reset();
        return output;
    }

    void installOutputHandler(ProgramExecutor program, Supplier<Boolean> condition) {
        if (partial.containsKey(program)) {
            throw new UnexpectedError("Cannot install output handler from the same program twice");
        }
        partial.put(program, new ConditionalOutput(condition));
    }

    void uninstallOutputHandler(ProgramExecutor program) {
        if (!partial.containsKey(program)) {
            throw new UnexpectedError("Cannot uninstall output handler that doesn't exist");
        }
        partial.remove(program);
    }

    private ByteArrayOutputStream getPartialHandler() {
        for (var handler : partial.values()) {
            if (handler.condition.get()) {
                return handler.output;
            }
        }
        return null;
    }

}
