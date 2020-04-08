package org.hyperskill.hstest.v7.dynamic;

import org.hyperskill.hstest.v7.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.v7.dynamic.output.SystemOutHandler;

import java.util.Locale;

import static java.lang.System.getSecurityManager;

public final class SystemHandler {

    private SystemHandler() { }

    private static SecurityManager oldSecurityManager;
    private static Locale oldLocale;
    private static String oldLineSeparator;

    private final static String separatorProperty = "line.separator";

    public static void setUpSystem() throws Exception {
        SystemOutHandler.replaceSystemOut();
        SystemInHandler.replaceSystemIn();

        oldSecurityManager = getSecurityManager();
        System.setSecurityManager(
            new TestingSecurityManager(
                oldSecurityManager,
                Thread.currentThread().getThreadGroup())
        );

        oldLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        oldLineSeparator = System.getProperty(separatorProperty);
        System.setProperty(separatorProperty, "\n");
    }

    public static void tearDownSystem() {
        SystemOutHandler.revertSystemOut();
        SystemInHandler.revertSystemIn();
        System.setSecurityManager(oldSecurityManager);
        Locale.setDefault(oldLocale);
        System.setProperty(separatorProperty, oldLineSeparator);
    }
}
