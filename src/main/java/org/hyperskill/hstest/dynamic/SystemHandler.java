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
    // private static String oldUserDir;

    private static final String separatorProperty = "line.separator";
    // private static final String userDirProperty = "user.dir";

    public static void setUp() {
        lockSystemForTesting();

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

        /*
        oldUserDir = System.getProperty(userDirProperty);
        File dir = new File(oldUserDir);
        if (dir.getName().equals("task")) {
            // EduTools when testing sets user dir to subproject,
            // but when the user is running their code user dir is set to root dir
            // Since testing should be consistent with running the code we should
            // revert back user dir to the root dir.
            dir = dir.getParentFile().getParentFile();
            System.setProperty(userDirProperty, dir.getAbsolutePath());
        }
        */
    }

    public static void tearDownSystem() {
        unlockSystemForTesting();

        OutputHandler.revertOutput();
        InputHandler.revertInput();
        System.setSecurityManager(oldSecurityManager);
        Locale.setDefault(oldLocale);
        System.setProperty(separatorProperty, oldLineSeparator);

        // System.setProperty(userDirProperty, oldUserDir);
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
}
