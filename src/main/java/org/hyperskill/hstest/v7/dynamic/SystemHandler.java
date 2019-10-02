package org.hyperskill.hstest.v7.dynamic;

import org.hyperskill.hstest.v7.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;
import org.junit.contrib.java.lang.system.internal.NoExitSecurityManager;

import static java.lang.System.getSecurityManager;

public class SystemHandler {
    private static SecurityManager oldSecurityManager;

    public static void setUpSystem() throws Exception {
        SystemOutHandler.replaceSystemOut();
        SystemInHandler.replaceSystemIn();
        oldSecurityManager = getSecurityManager();
        System.setSecurityManager(
            new NoExitSecurityManager(oldSecurityManager)
        );
    }

    public static void tearDownSystem() {
        SystemOutHandler.revertSystemOut();
        SystemInHandler.revertSystemIn();
        System.setSecurityManager(oldSecurityManager);
    }
}
