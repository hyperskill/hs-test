package org.hyperskill.hstest.dynamic;

import org.hyperskill.hstest.dynamic.input.InputHandler;
import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.dynamic.security.TestingSecurityManager;
import org.hyperskill.hstest.exception.outcomes.ErrorWithFeedback;
import org.hyperskill.hstest.testing.execution.ProgramExecutor;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import static java.lang.System.getSecurityManager;
import static org.hyperskill.hstest.common.JavaUtils.isSecurityManagerAllowed;

public final class SystemHandler {

    private SystemHandler() { }

    private static final AtomicBoolean locked = new AtomicBoolean(false);
    private static Thread lockerThread = null;

    private static SecurityManager oldSecurityManager;
    private static Locale oldLocale;
    private static String oldLineSeparator;
    private static String oldDefaultCharset;

    public static final String separatorProperty = "line.separator";
    public static final String defaultCharsetProperty = "file.encoding";

    public static void setUp() {
        lockSystemForTesting();

        OutputHandler.replaceOutput();
        InputHandler.replaceInput();

        if (isSecurityManagerAllowed()) {
            oldSecurityManager = getSecurityManager();
            System.setSecurityManager(new TestingSecurityManager(oldSecurityManager));
        }

        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        TestingSecurityManager.setTestingGroup(rootGroup);

        oldLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        oldLineSeparator = System.getProperty(separatorProperty);
        System.setProperty(separatorProperty, "\n");

        oldDefaultCharset = System.getProperty(defaultCharsetProperty);
        System.setProperty(defaultCharsetProperty, "UTF-8");
    }

    public static void tearDownSystem() {
        unlockSystemForTesting();

        OutputHandler.revertOutput();
        InputHandler.revertInput();

        if (isSecurityManagerAllowed()) {
            System.setSecurityManager(oldSecurityManager);
        }

        TestingSecurityManager.setTestingGroup(null);

        Locale.setDefault(oldLocale);
        System.setProperty(separatorProperty, oldLineSeparator);
        System.setProperty(defaultCharsetProperty, oldDefaultCharset);
    }

    private static void lockSystemForTesting() {
        boolean success = locked.compareAndSet(false, true);
        if (!success) {
            throw new ErrorWithFeedback("Cannot start the testing process more than once");
        }
        lockerThread = Thread.currentThread();
    }

    private static void unlockSystemForTesting() {
        if (Thread.currentThread() != lockerThread) {
            throw new ErrorWithFeedback("Cannot tear down the testing process from the other thread");
        }

        boolean success = locked.compareAndSet(true, false);
        if (!success) {
            throw new ErrorWithFeedback("Cannot tear down the testing process more than once");
        }
        lockerThread = null;
    }

    public static void installHandler(ProgramExecutor program, Supplier<Boolean> condition) {
        InputHandler.installInputHandler(program, condition);
        OutputHandler.installOutputHandler(program, condition);
    }

    public static void uninstallHandler(ProgramExecutor program) {
        InputHandler.uninstallInputHandler(program);
        OutputHandler.uninstallOutputHandler(program);
    }
}
