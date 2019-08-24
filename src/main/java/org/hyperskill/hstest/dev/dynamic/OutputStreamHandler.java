package org.hyperskill.hstest.dev.dynamic;

import java.io.PrintStream;
import java.nio.charset.Charset;

public class OutputStreamHandler {

    public static final PrintStream realOut = System.out;
    public static final ClonedOutputStream clonedSystemOut = new ClonedOutputStream(realOut);

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
    }

    public static String getOutput() {
        return clonedSystemOut.clonedStream.toString();
    }
}
