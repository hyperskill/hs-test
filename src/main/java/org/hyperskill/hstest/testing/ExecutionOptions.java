package org.hyperskill.hstest.testing;

import static org.hyperskill.hstest.common.JavaUtils.isUnderDebugger;

/**
 * Command-line options passed to the JVM listed in one place
 */
public final class ExecutionOptions {
    private ExecutionOptions() { }

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

    /**
     * In case of fatal error outcome, feedback contains OS, language version,
     * language vendor. In case of running a test inside docker, this information
     * is not relevant to the user and to the Hyperskill team and thus should be ignored.
     * If the test is to be run inside docker,
     * an argument "-DinsideDocker=true" should be passed
     */
    public static boolean insideDocker = Boolean.getBoolean("insideDocker");

    /**
     * It is hard to debug the tests because of the 15 seconds timeout.
     * To avoid any time limit timeouts, an argument "-DdebugMode=true" should be passed.
     * In case you are under the debugger, this field should be automatically set to "true".
     */
    public static boolean debugMode = Boolean.getBoolean("debugMode") || isUnderDebugger();

    /**
     * Enables SecurityManager even though it's not recommended being used in Java 17
     * In Java 8-16 it still will be used, on Java 17 it's not unless this flag is set to true.
     * Use "-DforceSecurityManager=true" to set this flag.
     */
    public static boolean forceSecurityManager = Boolean.getBoolean("forceSecurityManager");
}
