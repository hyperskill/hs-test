package org.hyperskill.hstest.v2.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Utils {
    private static final String CURRENT_DIR = System.getProperty("user.dir") + File.separator;

    private Utils() {}

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
        List<String> linesToShow = Arrays.stream(stackTrace.split("\\n"))
                .filter(line -> !line.contains("org.junit."))
                .takeWhile(line -> !line.contains("at java.base/jdk.internal.reflect"))
                .collect(Collectors.toList());
        return String.join("\n", linesToShow).trim();
    }

    public static void createFiles(Map<String, String> files) {
        files.forEach((filename, content) -> {
            try {
                Files.write(Paths.get(CURRENT_DIR + filename), content.getBytes());
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void deleteFiles(Map<String, String> files) {
        files.forEach((filename, content) -> {
            try {
                Files.deleteIfExists(Paths.get(CURRENT_DIR + filename));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public static String readFile(String name) {
        try {
            return Files.readString(Paths.get(CURRENT_DIR + name));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException ignored) {}
    }
}
