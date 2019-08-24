package org.hyperskill.hstest.dev.dynamic;

import org.hyperskill.hstest.dev.dynamic.output.OutputStreamHandler;

import static org.hyperskill.hstest.dev.common.Utils.normalizeLineEndings;

public class Main {

    public static String get() {
        return normalizeLineEndings(OutputStreamHandler.getOutput());
    }

    public static void main(String[] args) throws Exception {
        OutputStreamHandler.replaceSystemOut();
        System.setProperty("line.separator", "\n");
        System.out.println("123");
        String log1 = get();
        System.out.println("123");
        String log2 = get();
        OutputStreamHandler.resetOutput();
        System.out.println("1234");
        String log3 = get();
    }
}
