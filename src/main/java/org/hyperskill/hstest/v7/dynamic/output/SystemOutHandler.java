package org.hyperskill.hstest.v7.dynamic.output;

import org.hyperskill.hstest.v7.stage.StageTest;
import org.hyperskill.hstest.v7.testing.TestRun;

import java.io.PrintStream;
import java.nio.charset.Charset;

import static org.hyperskill.hstest.v7.common.Utils.cleanText;

public final class SystemOutHandler {

    private SystemOutHandler() { }

    private static final PrintStream REAL_OUT = System.out;
    private static final SystemOutMock MOCK_OUT = new SystemOutMock(REAL_OUT);

    public static PrintStream getRealOut() {
        return REAL_OUT;
    }

    public static void replaceSystemOut() throws Exception {
        System.setOut(new PrintStream(
            MOCK_OUT, true, Charset.defaultCharset().name()));
    }

    public static void revertSystemOut() {
        resetOutput();
        System.setOut(REAL_OUT);
    }

    public static void resetOutput() {
        MOCK_OUT.reset();
    }

    public static String getOutput() {
        return cleanText(MOCK_OUT.getClonedOut().toString());
    }

    public static String getPartialOutput() {
        String output = cleanText(MOCK_OUT.getPartialOut().toString());
        MOCK_OUT.getPartialOut().reset();
        return output;
    }

    public static String getDynamicOutput() {
        return cleanText(MOCK_OUT.getDynamicOut().toString());
    }

    public static void injectInput(String input) {
        TestRun testRun = StageTest.getCurrTestRun();
        if (testRun != null) {
            testRun.setInputUsed();
        }
        MOCK_OUT.injectInput(input);
    }
}
