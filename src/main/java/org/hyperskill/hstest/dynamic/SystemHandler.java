package org.hyperskill.hstest.dynamic;

import org.hyperskill.hstest.dynamic.input.SystemInHandler;
import org.hyperskill.hstest.dynamic.output.SystemOutHandler;

import java.util.Locale;

import static java.lang.System.getSecurityManager;

public final class SystemHandler {

    private SystemHandler() { }

    private static SecurityManager oldSecurityManager;
    private static Locale oldLocale;
    private static String oldLineSeparator;

    private final static String separatorProperty = "line.separator";

    public static void setUpSystem() {
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
