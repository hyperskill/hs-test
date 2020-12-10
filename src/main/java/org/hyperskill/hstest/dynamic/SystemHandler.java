package org.hyperskill.hstest.dynamic;

import org.hyperskill.hstest.dynamic.input.InputHandler;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.dynamic.security.TestingSecurityManager;

import java.util.Locale;

import static java.lang.System.getSecurityManager;

public final class SystemHandler {

    private SystemHandler() { }

    private static SecurityManager oldSecurityManager;
    private static Locale oldLocale;
    private static String oldLineSeparator;

    private static final String separatorProperty = "line.separator";

    public static void setUp() {
        OutputHandler.replaceOutput();
        InputHandler.replaceInput();

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
        OutputHandler.revertOutput();
        InputHandler.revertInput();
        System.setSecurityManager(oldSecurityManager);
        Locale.setDefault(oldLocale);
        System.setProperty(separatorProperty, oldLineSeparator);
    }
}
