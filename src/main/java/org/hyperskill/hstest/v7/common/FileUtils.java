package org.hyperskill.hstest.v7.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class FileUtils {

    private FileUtils() {}

    public static final String CURRENT_DIR = System.getProperty("user.dir") + File.separator;
    private static final String TEMP_FILE_PREFIX = "hyperskill-temp-file-";

    private static final Set<String> RETURNED_NONEXISTENT_FILES = new HashSet<>();

    public static void createFiles(Map<String, String> files) throws IOException {
        for (Map.Entry<String, String> fileEntry : files.entrySet()) {
            String filename = fileEntry.getKey();
            String content = fileEntry.getValue();
            Files.write(Paths.get(CURRENT_DIR + filename), content.getBytes());
        }
    }

    public static void deleteFiles(Map<String, String> files) throws IOException {
        for (Map.Entry<String, String> fileEntry : files.entrySet()) {
            String filename = fileEntry.getKey();
            Files.deleteIfExists(Paths.get(CURRENT_DIR + filename));
        }
    }

    private static String normalizeFileExtension(final String extension) {
        if (extension == null || extension.isEmpty()) {
            return "";
        }

        if (extension.charAt(0) != '.') {
            return "." + extension;
        }

        return extension;
    }

    public static String getNonexistentFilePath(String extension) {
        extension = normalizeFileExtension(extension);

        int i = 0;

        while (true) {
            final String fileName = TEMP_FILE_PREFIX + i + extension;
            final Path path = Paths.get(CURRENT_DIR + fileName);

            if (!RETURNED_NONEXISTENT_FILES.contains(fileName) && Files.notExists(path)) {
                RETURNED_NONEXISTENT_FILES.add(fileName);
                return path.toAbsolutePath().toString();
            } else {
                ++i;
            }
        }
    }

    public static String getNonexistentFilePath() {
        return getNonexistentFilePath(null);
    }

    public static String readFile(String name) {
        try {
            Path path = Paths.get(CURRENT_DIR + name);
            return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            //return Files.readString(path); <- Java 11
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
