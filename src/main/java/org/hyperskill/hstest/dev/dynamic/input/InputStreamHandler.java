package org.hyperskill.hstest.dev.dynamic.input;

import java.io.InputStream;

public class InputStreamHandler {

    public static InputStream realIn = System.in;
    public static SystemInMock mockIn = new SystemInMock();

    public static void replaceSystemIn() {
        System.setIn(mockIn);
    }

    public static void setInput(String input) {
        mockIn.provideText(input);
    }

    public static void revertSystemIn() {
        System.setIn(realIn);
    }

}
