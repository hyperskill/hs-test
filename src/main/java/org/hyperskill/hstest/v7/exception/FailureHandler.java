package org.hyperskill.hstest.v7.exception;

import java.lang.reflect.InvocationTargetException;

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
        String os = System.getProperty("os.name");
        String java = System.getProperty("java.version");
        String vendor = System.getProperty("java.vendor");

        return "OS " + os + "\n"
            + "Java " + java + "\n"
            + "Vendor " + vendor + "\n"
            + "Testing library version 7";
    }
}
