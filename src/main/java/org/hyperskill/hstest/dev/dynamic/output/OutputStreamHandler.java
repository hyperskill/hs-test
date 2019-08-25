package org.hyperskill.hstest.dev.dynamic.output;

import java.io.PrintStream;
import java.nio.charset.Charset;

public class OutputStreamHandler {

    public static final PrintStream realOut = System.out;
    public static final SystemOutMock clonedSystemOut = new SystemOutMock(realOut);

    public static void replaceSystemOut() throws Exception {
        System.setOut(new PrintStream(
            clonedSystemOut, true, Charset.defaultCharset().name()));
    }

    public static void revertSystemOut() {
        resetOutput();
        System.setOut(realOut);
    }

    public static void resetOutput() {
        clonedSystemOut.clonedStream.reset();
        clonedSystemOut.dynamicStream.reset();
    }

    public static String getOutput() {
        return clonedSystemOut.clonedStream.toString();
    }

    public static String getDynamicOutput() {
        String output = clonedSystemOut.dynamicStream.toString();
        clonedSystemOut.dynamicStream.reset();
        return output;
    }
}
