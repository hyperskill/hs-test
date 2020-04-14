package org.hyperskill.hstest.v6.dynamic.output;

import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.TestRun;

import java.io.PrintStream;
import java.nio.charset.Charset;

import static org.hyperskill.hstest.v6.common.Utils.normalizeLineEndings;

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
        return normalizeLineEndings(mockOut.clonedStream.toString());
    }

    public static String getDynamicOutput() {
        String output = normalizeLineEndings(mockOut.dynamicStream.toString());
        mockOut.dynamicStream.reset();
        return output;
    }

    public static String getOutputWithInputInjected() {
        return normalizeLineEndings(mockOut.withInputInjectedStream.toString());
    }

    public static void injectInput(String input) {
        TestRun testRun = BaseStageTest.getCurrTestRun();
        if (testRun != null) {
            testRun.setInputUsed();
        }
        mockOut.injectInput(input);
    }
}
