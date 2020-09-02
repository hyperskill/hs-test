package org.hyperskill.hstest.exception;

import java.lang.reflect.InvocationTargetException;

import static org.hyperskill.hstest.testing.ExecutionOptions.insideDocker;

public final class FailureHandler {

    private FailureHandler() { }

    public static boolean isUserFailed(Throwable t) {
        // If user failed then t == InvocationTargetException
        // and t.getCause() == Actual user exception
        return t.getCause() != null
            && t instanceof InvocationTargetException;
    }

    public static Throwable getUserException(Throwable t) {
        if (isUserFailed(t)) {
            return t.getCause();
        }
        return null;
    }

    public static String getReport() {
        if (!insideDocker) {
            String os = System.getProperty("os.name");
            String java = System.getProperty("java.version");
            String vendor = System.getProperty("java.vendor");

            return "Submitted via IDE\n"
                + "\n"
                + "OS " + os + "\n"
                + "Java " + java + "\n"
                + "Vendor " + vendor + "\n"
                + "Testing library version 7.2";
        } else {
            return "Submitted via web";
        }
    }
}
