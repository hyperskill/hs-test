package org.hyperskill.hstest.dynamic;

import org.hyperskill.hstest.dynamic.input.InputHandler;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.dynamic.security.TestingSecurityManager;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.getSecurityManager;

public final class SystemHandler {

    private SystemHandler() { }

    private static final AtomicBoolean locked = new AtomicBoolean(false);
    private static Thread lockerThread = null;

    private static SecurityManager oldSecurityManager;
    private static Locale oldLocale;
    private static String oldLineSeparator;

    private static final String separatorProperty = "line.separator";

    public static void setUp() {
        boolean success = locked.compareAndSet(false, true);
        if (!success) {
            throw new ErrorWithFeedback("Cannot start the testing process more than once");
        }
        lockerThread = Thread.currentThread();

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
        if (Thread.currentThread() != lockerThread) {
            throw new ErrorWithFeedback("Cannot tear down the testing process from the other thread");
        }

        boolean success = locked.compareAndSet(true, false);
        if (!success) {
            throw new ErrorWithFeedback("Cannot tear down the testing process more than once");
        }
        lockerThread = null;

        OutputHandler.revertOutput();
        InputHandler.revertInput();
        System.setSecurityManager(oldSecurityManager);
        Locale.setDefault(oldLocale);
        System.setProperty(separatorProperty, oldLineSeparator);
    }
}
