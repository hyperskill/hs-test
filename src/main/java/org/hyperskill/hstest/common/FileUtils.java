package org.hyperskill.hstest.common;

import lombok.Data;
import org.hyperskill.hstest.dynamic.SystemHandler;
import org.hyperskill.hstest.exception.outcomes.UnexpectedError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

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
            Files.write(Paths.get(abspath(filename)), content.getBytes());
        }
    }

    public static void deleteFiles(Map<String, String> files) throws IOException {
        for (Map.Entry<String, String> fileEntry : files.entrySet()) {
            String filename = fileEntry.getKey();
            Files.deleteIfExists(Paths.get(abspath(filename)));
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
            final Path path = Paths.get(abspath(fileName));

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
        Path path = Paths.get(abspath(name));
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

    public static boolean hasJavaSolution(String folder) {
        var currFolder = abspath(folder);
        var srcFolder = join(currFolder, "src");

        if (!isdir(srcFolder)) {
            return false;
        }

        for (var src : walkUserFiles(srcFolder)) {
            for (var file : src.files) {
                if (file.getName().endsWith(".java") ||
                        file.getName().endsWith(".kt") ||
                        file.getName().endsWith(".scala")
                ) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Iterable<Folder> walkUserFiles(String folder) {
        var currFolder = abspath(folder);
        var testFolder = join(currFolder, "test");

        Iterator<Path> walk;

        try {
            walk = Files.walk(Paths.get(currFolder))
                .filter(Files::isDirectory)
                .filter(path -> !path.startsWith(Paths.get(testFolder)))
                .iterator();
        } catch (IOException ex) {
            throw new UnexpectedError("Error while walking in " + folder, ex);
        }

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

    public static String displaySorted(Collection<File> files) {
        return files.stream()
            .map(FileUtils::abspath)
            .sorted()
            .map(s -> "\"" + s + "\"")
            .collect(Collectors.joining(", "));
    }

    // Implementations of Python's "os.path.*" functions
    // since Java has no straightforward way to navigate around filesystem.

    public static String cwd() {
        return abspath(System.getProperty(SystemHandler.workingDirectoryProperty));
    }

    public static void chdir(String folder) {
        chdir(new File(folder));
    }

    public static void chdir(File folder) {
        System.setProperty(SystemHandler.workingDirectoryProperty, abspath(folder));
    }

    public static String abspath(String path) {
        String cwd = System.getProperty(SystemHandler.workingDirectoryProperty);
        if (path.startsWith(cwd)) {
            return path;
        }
        return abspath(new File(cwd, path));
    }

    public static String abspath(File file) {
        return file.getAbsolutePath();
    }

    public static String join(String folder, String file) {
        return join(new File(folder), file);
    }

    public static String join(File folder, String file) {
        return abspath(new File(abspath(folder), file));
    }

    public static boolean exists(String path) {
        return new File(abspath(path)).exists();
    }

    public static boolean isdir(String path) {
        return new File(abspath(path)).isDirectory();
    }

    public static boolean isfile(String path) {
        return new File(abspath(path)).isFile();
    }
}
