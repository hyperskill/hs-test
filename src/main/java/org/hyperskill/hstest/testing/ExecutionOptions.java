package org.hyperskill.hstest.testing;

/**
 * Command-line options passed to the JVM listed in one place
 */
public class ExecutionOptions {
    /**
     * Unit tests contain slow tests (time limit tests)
     * and to skip them an argument "-DskipSlow=true" should be passed
     *
     * To mark the test as slow you should write the following code:
     *  \@BeforeClass
     *   public static void stopSlow() {
     *       Assume.assumeFalse(skipSlow);
     *   }
     */
    public static final boolean skipSlow = Boolean.getBoolean("skipSlow");

    /**
     * Unit tests can produce a lot of output that are printed to stdout.
     * It may impact performance and if you don't want output to be printed
     * an argument "-DignoreStdout=true" should be passed
     */
    public static final boolean ignoreStdout = Boolean.getBoolean("ignoreStdout");
}
