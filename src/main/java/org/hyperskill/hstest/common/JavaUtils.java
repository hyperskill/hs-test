package org.hyperskill.hstest.common;

import java.lang.management.ManagementFactory;

import static org.hyperskill.hstest.testing.ExecutionOptions.forceSecurityManager;

public class JavaUtils {

    private JavaUtils() { }

    /**
     * Thanks to https://stackoverflow.com/a/2591122/13160001
     * @return Java version as integer.
     */
    public static int getJavaVersion() {
        String version = System.getProperty("java.version");

        if (version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) {
                version = version.substring(0, dot);
            }
        }

        return Integer.parseInt(version);
    }

    public static boolean isUnderDebugger() {
        return ManagementFactory.getRuntimeMXBean()
            .getInputArguments().toString().contains("jdwp");
    }

    public static boolean isSecurityManagerAllowed() {
        return getJavaVersion() < 17 || forceSecurityManager;
    }

    /**
     * In the future, other ways of preventing System.exit() will be implemented
     * Now it's only one way: using custom SecurityManager
     */
    public static boolean isSystemExitAllowed() {
        return isSecurityManagerAllowed();
    }

}
