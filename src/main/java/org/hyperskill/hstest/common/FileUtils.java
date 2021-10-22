package org.hyperskill.hstest.common;

import lombok.Data;
import org.hyperskill.hstest.dynamic.SystemHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public final class FileUtils {

    private FileUtils() { }

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
        if (!name.startsWith(CURRENT_DIR)) {
            name = CURRENT_DIR + name;
        }
        Path path = Paths.get(name);
        try {
            return Files.readString(path);
        } catch (IOException ignored) {
            return null;
        }
    }

    @Data
    public static class Folder {
        final File folder;
        final List<File> dirs;
        final List<File> files;
    }

    public static Iterable<Folder> walkUserFiles(String folder) throws IOException {
        var currFolder = new File(folder).getAbsolutePath();
        var testFolder = new File(currFolder, "test").getAbsolutePath();

        Iterator<Path> walk = Files.walk(Paths.get(currFolder))
            .filter(Files::isDirectory)
            .filter(path -> !path.startsWith(Paths.get(testFolder)))
            .iterator();

        return () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                return walk.hasNext();
            }

            @Override
            public Folder next() {
                Path next = walk.next();

                if (next == null) {
                    throw new NoSuchElementException();
                }

                File[] children = next.toFile().listFiles();

                if (children == null) {
                    throw new NoSuchElementException();
                }

                List<File> listChildren = List.of(children);

                List<File> dirs = listChildren.stream().filter(File::isDirectory).collect(toList());
                List<File> files = listChildren.stream().filter(File::isFile).collect(toList());

                return new FileUtils.Folder(next.toFile(), dirs, files);
            }
        };
    }

    public static String cwd() {
        return new File(System.getProperty(SystemHandler.separatorProperty)).getAbsolutePath();
    }

    public static void chdir(String folder) {
        chdir(new File(folder));
    }

    public static void chdir(File folder) {
        System.setProperty(SystemHandler.separatorProperty, folder.getAbsolutePath());
    }

}
