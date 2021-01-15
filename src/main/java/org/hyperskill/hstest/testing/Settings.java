package org.hyperskill.hstest.testing;

public class Settings {
    private Settings() { }

    /**
     * Handles resetting output after each test.
     *
     * If true, library will show output only of the failed test
     * If false, library will show output of all tests
     *
     * For example, in Spring tests library sets this field to false
     */
    public static boolean doResetOutput = true;

    /**
     * Handles out-of-input state when testing the user's program.
     *
     * If true, treats out-of-input as a normal situation while testing user program.
     *  The library will simulate out-of-input like it's reading a file.
     * If false, treats out-of-input as an immediate fail.
     */
    public static boolean allowOutOfInput = false;
}
