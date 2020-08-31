package org.hyperskill.hstest.dynamic.output;

import org.hyperskill.hstest.common.Utils;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.TestRun;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public final class SystemOutHandler {

    private SystemOutHandler() { }

    private static final PrintStream REAL_OUT = System.out;
    private static final PrintStream REAL_ERR = System.err;

    private static final SystemOutMock MOCK_OUT = new SystemOutMock(REAL_OUT);
    private static final SystemOutMock MOCK_ERR = new SystemOutMock(REAL_ERR);

    public static PrintStream getRealOut() {
        return MOCK_OUT.getOriginal();
    }

    public static PrintStream getRealErr() {
        return MOCK_ERR.getOriginal();
    }

    public static void replaceSystemOut() {
        System.setOut(new PrintStream(MOCK_OUT, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(MOCK_ERR, true, StandardCharsets.UTF_8));
    }

    public static void revertSystemOut() {
        resetOutput();
        System.setOut(REAL_OUT);
        System.setErr(REAL_ERR);
    }

    public static void resetOutput() {
        MOCK_OUT.reset();
        MOCK_ERR.reset();
    }

    public static String getOutput() {
        return Utils.cleanText(new String(MOCK_OUT.getClonedOut().toByteArray(), StandardCharsets.UTF_8));
    }

    public static String getDynamicOutput() {
        return Utils.cleanText(new String(MOCK_OUT.getDynamicOut().toByteArray(), StandardCharsets.UTF_8));
    }

    public static String getPartialOutput(ThreadGroup group) {
        String output = Utils.cleanText(new String(MOCK_OUT.getPartialOut(group).toByteArray(), StandardCharsets.UTF_8));
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
