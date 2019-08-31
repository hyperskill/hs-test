package org.hyperskill.hstest.dev.dynamic.output;

import java.io.PrintStream;
import java.nio.charset.Charset;

public class SystemOutHandler {

    private static final PrintStream realOut = System.out;
    private static final SystemOutMock mockOut = new SystemOutMock(realOut);

    public static PrintStream getRealOut() {
        return realOut;
    }

    public static void replaceSystemOut() throws Exception {
        System.setOut(new PrintStream(
            mockOut, true, Charset.defaultCharset().name()));
    }

    public static void revertSystemOut() {
        resetOutput();
        System.setOut(realOut);
    }

    public static void resetOutput() {
        mockOut.clonedStream.reset();
        mockOut.dynamicStream.reset();
        mockOut.withInputInjectedStream.reset();
    }

    public static String getOutput() {
        return mockOut.clonedStream.toString();
    }

    public static String getDynamicOutput() {
        String output = mockOut.dynamicStream.toString();
        mockOut.dynamicStream.reset();
        return output;
    }

    public static String getOutputWithInputInjected() {
        return mockOut.withInputInjectedStream.toString();
    }

    public static void injectInput(String input) {
        mockOut.injectInput(input);
    }
}
