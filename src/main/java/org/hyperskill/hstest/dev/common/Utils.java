package org.hyperskill.hstest.dev.common;

import org.hyperskill.hstest.dev.testcase.Process;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class Utils {
    private static final String CURRENT_DIR = System.getProperty("user.dir") + File.separator;

    private static final String TEMP_FILE_PREFIX = "hyperskill-temp-file-";

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

    private static String normalizeFileExtension(final String extension) {
        if (extension == null || "".equals(extension)) {
            return "";
        }

        if (extension.charAt(0) != '.') {
            return "." + extension;
        }

        return extension;
    }

    public static File getNonexistentFile(String extension) {
        extension = normalizeFileExtension(extension);

        long i = 0;

        while (true) {
            final String fileName = TEMP_FILE_PREFIX + i + extension;

            final Path path = Paths.get(CURRENT_DIR + fileName);

            if (!Files.exists(path)) {
                return path.toFile();
            } else {
                ++i;
            }
        }
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

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException ignored) {}
    }

    public static ExecutorService startThreads(List<Process> processes) {
        int poolSize = processes.size();
        if (poolSize == 0) {
            return null;
        }
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for (Process process : processes) {
            process.start();
            executor.submit(process);
            while (!process.isStarted()) {
                sleep(10);
            }
        }
        return executor;
    }

    public static void stopThreads(List<Process> processes, ExecutorService executor) {
        if (executor == null) {
            return;
        }
        try {
            for (Process process : processes) {
                process.stop();
            }
            executor.shutdown();
            boolean terminated = executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            if (!terminated) {
                executor.shutdownNow();
                terminated = executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
                if (!terminated) {
                    System.err.println("SOME PROCESSES ARE NOT TERMINATED");
                }
            }
            for (int i = 1; i <= processes.size(); i++) {
                Process process = processes.get(i - 1);
                if (!process.isStopped()) {
                    System.err.println("PROCESS #" + i + " IS NOT TERMINATED");
                }
            }
        } catch (InterruptedException ex) {
            // ignored
        }
    }

    public static String getUrlPage(String url) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            InputStream inputStream = new URL(url).openStream();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String nextLine;
            String newLine = System.getProperty("line.separator");
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
                stringBuilder.append(newLine);
            }
            return stringBuilder.toString()
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n").strip();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String normalizeLineEndings(String str) {
        return str
            .replaceAll("\r\n", "\n")
            .replaceAll("\r", "\n");
    }
}
