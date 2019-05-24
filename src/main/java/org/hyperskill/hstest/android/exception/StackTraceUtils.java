package org.hyperskill.hstest.android.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class StackTraceUtils {

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * It prepares a given stacktrace to display it for learners.
     *
     * If user calls System.exit(0) the stacktrace starts with org.junit that should be skipped.
     * We're showing all user's stack trace up to our reflect methods.
     */
    public static String filterStackTrace(final String stackTrace) {
        List<String> linesToShow = new ArrayList<>();
        for (String line : stackTrace.split("\n")) {
            if (line.contains("org.junit.")) {
                continue;
            }
            if (line.contains("at java.base/jdk.internal.reflect")) {
                break;
            }
            linesToShow.add(line);
        }
        return String.join("\n", linesToShow).trim();
    }

    public static String removeNonLibraryClasses(final String stackTrace) {
        List<String> linesToShow = new ArrayList<>();
        int index = 0;
        for (String line : stackTrace.split("\n")) {
            if (index < 3 || line.contains("at org.hyperskill.hstest")) {
                linesToShow.add(line);
            }
            index++;
        }
        return String.join("\n", linesToShow).trim();
    }
}
