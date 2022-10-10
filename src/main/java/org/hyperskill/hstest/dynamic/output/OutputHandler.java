package org.hyperskill.hstest.dynamic.output;

import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testing.TestRun;
import org.hyperskill.hstest.testing.execution.ProgramExecutor;

import java.io.PrintStream;
import java.util.function.Supplier;

import static org.hyperskill.hstest.common.Utils.cleanText;

public final class OutputHandler {

    private OutputHandler() { }

    private static final PrintStream REAL_OUT = System.out;
    private static final PrintStream REAL_ERR = System.err;

    private static final OutputMock MOCK_OUT = new OutputMock(REAL_OUT);
    private static final OutputMock MOCK_ERR = new OutputMock(REAL_ERR);

    public static void print(String text) {
        getRealOut().println(text);
    }

    public static PrintStream getRealOut() {
        return MOCK_OUT.getOriginal();
    }

    public static PrintStream getRealErr() {
        return MOCK_ERR.getOriginal();
    }

    public static void replaceOutput() {
        System.setOut(new PrintStream(MOCK_OUT, true));
        System.setErr(new PrintStream(MOCK_ERR, true));
    }

    public static void revertOutput() {
        resetOutput();
        System.setOut(REAL_OUT);
        System.setErr(REAL_ERR);
    }

    public static void resetOutput() {
        MOCK_OUT.reset();
        MOCK_ERR.reset();
    }

    public static String getOutput() {
        return cleanText(MOCK_OUT.getCloned());
    }

    public static String getErr() {
        return cleanText(MOCK_ERR.getCloned());
    }

    public static String getDynamicOutput() {
        return cleanText(MOCK_OUT.getDynamic());
    }

    public static String getPartialOutput(ProgramExecutor program) {
        return cleanText(MOCK_OUT.getPartial(program));
    }

    public static void injectInput(String input) {
        TestRun testRun = StageTest.getCurrTestRun();
        if (testRun != null) {
            testRun.setInputUsed();
        }
        MOCK_OUT.injectInput(input);
    }

    public static void installOutputHandler(ProgramExecutor program, Supplier<Boolean> condition) {
        MOCK_OUT.installOutputHandler(program, condition);
        MOCK_ERR.installOutputHandler(program, condition);
    }

    public static void uninstallOutputHandler(ProgramExecutor program) {
        MOCK_OUT.uninstallOutputHandler(program);
        MOCK_ERR.uninstallOutputHandler(program);
    }


}
