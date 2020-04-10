package org.hyperskill.hstest.dynamic.output;

import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.TestRun;

import java.io.PrintStream;
import java.nio.charset.Charset;

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
        return Utils.cleanText(MOCK_OUT.getClonedOut().toString());
    }

    public static String getDynamicOutput() {
        return Utils.cleanText(MOCK_OUT.getDynamicOut().toString());
    }

    public static String getPartialOutput(ThreadGroup group) {
        String output = Utils.cleanText(MOCK_OUT.getPartialOut(group).toString());
        MOCK_OUT.getPartialOut(group).reset();
        return output;
    }

    public static void injectInput(String input) {
        TestRun testRun = StageTest.getCurrTestRun();
        if (testRun != null) {
            testRun.setInputUsed();
        }
        MOCK_OUT.injectInput(input);
    }
}
