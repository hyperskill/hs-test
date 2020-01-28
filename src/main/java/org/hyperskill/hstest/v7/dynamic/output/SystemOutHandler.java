package org.hyperskill.hstest.v7.dynamic.output;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testcase.TestRun;

import java.io.PrintStream;
import java.nio.charset.Charset;

import static org.hyperskill.hstest.v7.common.Utils.normalizeLineEndings;


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
        mockOut.reset();
    }

    public static String getOutput() {
        return normalizeLineEndings(mockOut.cloned.toString());
    }

    public static String getPartialOutput() {
        String output = normalizeLineEndings(mockOut.partial.toString());
        mockOut.partial.reset();
        return output;
    }

    public static String getDynamicOutput() {
        return normalizeLineEndings(mockOut.dynamic.toString());
    }

    public static void injectInput(String input) {
        TestRun testRun = StageTest.getCurrTestRun();
        if (testRun != null) {
            testRun.setInputUsed();
        }
        mockOut.injectInput(input);
    }
}
